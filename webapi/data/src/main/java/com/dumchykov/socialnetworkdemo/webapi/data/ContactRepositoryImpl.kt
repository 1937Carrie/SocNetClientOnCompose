package com.dumchykov.socialnetworkdemo.webapi.data

import com.dumchykov.data.Contact
import com.dumchykov.database.repository.DatabaseRepositoryImpl
import com.dumchykov.datastore.data.DataStoreProvider
import com.dumchykov.socialnetworkdemo.webapi.domain.ContactApiService
import com.dumchykov.socialnetworkdemo.webapi.domain.ContactRepository
import com.dumchykov.socialnetworkdemo.webapi.domain.ResponseState
import com.dumchykov.socialnetworkdemo.webapi.domain.models.ContactId
import com.dumchykov.socialnetworkdemo.webapi.domain.models.EmailPassword
import com.dumchykov.socialnetworkdemo.webapi.domain.models.toApiContact
import com.dumchykov.socialnetworkdemo.webapi.domain.models.toContact
import kotlinx.coroutines.flow.first

class ContactRepositoryImpl(
    private val contactApiService: ContactApiService,
    private val databaseRepository: DatabaseRepositoryImpl,
    private val dataStoreProvider: DataStoreProvider,
) : ContactRepository {
    override suspend fun register(email: String, password: String): ResponseState {
        return try {
            val (_, code, message, data) = contactApiService.register(email, password)
            if (code == 200) {
                val contact = data.user.toContact()
                databaseRepository.insertCurrentUser(contact)

                dataStoreProvider.saveAccessToken(data.accessToken)
                dataStoreProvider.saveRefreshToken(data.refreshToken)
                ResponseState.Success<Nothing>()
            } else {
                ResponseState.HttpCode(code, message)
            }
        } catch (e: Throwable) {
            ResponseState.Error(e.message)
        }
    }

    override suspend fun authorize(email: String, password: String): ResponseState {
        return try {
            val emailPassword = EmailPassword(email, password)
            val (_, code, message, data) = contactApiService.authorize(emailPassword)
            if (code == 200) {
                val contact = data.user.toContact()
                databaseRepository.insertCurrentUser(contact)

                dataStoreProvider.saveAccessToken(data.accessToken)
                dataStoreProvider.saveRefreshToken(data.refreshToken)
                ResponseState.Success<Nothing>()
            } else {
                ResponseState.HttpCode(code, message)
            }
        } catch (e: Throwable) {
            ResponseState.Error(e.message)
        }
    }

    override suspend fun refreshToken(): ResponseState {
        return try {
            val refreshToken = dataStoreProvider.readRefreshToken().first()
            val (_, code, message, data) = contactApiService.refreshToken(refreshToken)
            if (code == 200) {
                dataStoreProvider.saveAccessToken(data.accessToken)
                dataStoreProvider.saveRefreshToken(data.refreshToken)
                ResponseState.Success<Nothing>()
            } else {
                ResponseState.HttpCode(code, message)
            }
        } catch (e: Throwable) {
            ResponseState.Error(e.message)
        }
    }

    override suspend fun editUser(user: Contact): ResponseState {
        return try {
            val userId = getUserId()
            val bearerToken = dataStoreProvider.readAccessToken().first()
            val (_, code, message, data) = contactApiService.editUser(
                userId,
                bearerToken,
                user.toApiContact()
            )
            if (code == 200) {
                val contact = data.user.toContact()
                databaseRepository.insertCurrentUser(contact)
                getUsers()

                ResponseState.Success<Nothing>()
            } else {
                ResponseState.HttpCode(code, message)
            }
        } catch (e: Throwable) {
            ResponseState.Error(e.message)
        }
    }

    override suspend fun getUsers(): ResponseState {
        return try {
            val bearerToken = dataStoreProvider.readAccessToken().first()
            val (_, code, message, data) = contactApiService.getUsers(bearerToken)
            if (code == 200) {
                val users = data.users.map { it.toContact() }

                databaseRepository.insertAll(users)

                ResponseState.Success<Nothing>()
            } else {
                ResponseState.HttpCode(code, message)
            }
        } catch (e: Throwable) {
            ResponseState.Error(e.message)
        }
    }

    override suspend fun getUserById(id: Int): Contact {
        return if (id == -1) {
            databaseRepository.getCurrentUser()
        } else {
            databaseRepository.getUserById(id)
        }
    }

    override suspend fun addContact(contactId: Int): ResponseState {
        return try {
            val bearerToken = dataStoreProvider.readAccessToken().first()
            val userId = getUserId()
            val contactId1 = ContactId(contactId)
            val (_, code, message, data) = contactApiService.addContact(
                bearerToken,
                userId,
                contactId1
            )
            if (code == 200) {
                ResponseState.Success(data.apiContacts.map { it.toContact() })
            } else {
                ResponseState.HttpCode(code, message)
            }
        } catch (e: Throwable) {
            ResponseState.Error(e.message)
        }
    }

    override suspend fun deleteContact(contactId: Int): ResponseState {
        val userId = getUserId()
        val bearerToken = dataStoreProvider.readAccessToken().first()
        return try {
            val (_, code, message, data) = contactApiService.deleteContact(
                userId,
                contactId,
                bearerToken
            )
            if (code == 200) {
                ResponseState.Success(data.apiContacts.map { it.toContact() })
            } else {
                ResponseState.HttpCode(code, message)
            }
        } catch (e: Throwable) {
            ResponseState.Error(e.message)
        }
    }

    override suspend fun getUserContacts(): ResponseState {
        return try {
            val userId = getUserId()
            val bearerToken = dataStoreProvider.readAccessToken().first()
            val (_, code, message, data) = contactApiService.getUserContacts(userId, bearerToken)
            if (code == 200) {
                val contactIdList = data.apiContacts.map { it.id }
                val userContacts = databaseRepository.getUserContacts(contactIdList)
                ResponseState.Success(userContacts)
            } else {
                ResponseState.HttpCode(code, message)
            }
        } catch (e: Throwable) {
            ResponseState.Error(e.message)
        }
    }

    private suspend fun getUserId(): Int {
        val id = databaseRepository.getCurrentUser().id
        return id
    }
}