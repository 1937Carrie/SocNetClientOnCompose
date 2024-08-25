package com.dumchykov.socialnetworkdemo.webapi.data

import android.util.Log
import com.dumchykov.data.toContactDBO
import com.dumchykov.database.ContactsDatabase
import com.dumchykov.database.models.CurrentUserDBO
import com.dumchykov.datastore.data.DataStoreProvider
import com.dumchykov.socialnetworkdemo.webapi.data.interceptors.toContact
import com.dumchykov.socialnetworkdemo.webapi.domain.ContactApiService
import com.dumchykov.socialnetworkdemo.webapi.domain.ContactRepository
import com.dumchykov.socialnetworkdemo.webapi.domain.models.Contact
import com.dumchykov.socialnetworkdemo.webapi.domain.models.ContactId
import com.dumchykov.socialnetworkdemo.webapi.domain.models.ContactResponse
import com.dumchykov.socialnetworkdemo.webapi.domain.models.EmailPassword
import com.dumchykov.socialnetworkdemo.webapi.domain.models.SingleUserResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext

class ContactRepositoryImpl(
    private val contactApiService: ContactApiService,
    private val dataBase: ContactsDatabase,
    private val dataStoreProvider: DataStoreProvider,
) : ContactRepository {
    override suspend fun register(email: String, password: String, saveCredentials: Boolean) {
        try {
            val registerResponse = contactApiService.register(email, password)
            if (saveCredentials) {
                dataStoreProvider.saveCredentials(email, password)
            }

        } catch (e: Exception) {

        }
    }

    override suspend fun authorize(
        email: String,
        password: String,
    ): Boolean {
        val authResponse: ContactResponse<SingleUserResponse> = contactApiService.authorize(
            EmailPassword(email, password)
        )
        if (authResponse.code == 200) {
            val currentUser = CurrentUserDBO(
                id = 0,
                currentUserId = authResponse.data.user.id,
                contactsIds = emptyList()
            )
            withContext(Dispatchers.IO) {
                async { dataBase.currentUserDAO().insert(currentUser) }.await()

                async { dataStoreProvider.saveAccessToken(authResponse.data.accessToken) }.await()
                async { dataStoreProvider.saveRefreshToken(authResponse.data.refreshToken) }.await()

                getUsers()
                updateUserContacts()
            }
        }
        return authResponse.code == 200
    }

    override suspend fun refreshToken() {}

    override suspend fun getUser(): Contact {
        val currentUserId = dataBase.currentUserDAO().getCurrentUserId()

        val user = dataBase.contactsDao().getUser(currentUserId)

        return user?.toContact() ?: throw IllegalStateException()
    }

    override suspend fun editUser(user: Contact) {
        val userId = getUserId()
        val token = getAccessToken()
        val editedUserResponse = contactApiService.editUser(
            userId = userId,
            bearerToken = token,
            user = user
        )
        if (editedUserResponse.code == 200) {
            getUsers()
        }
    }

    override suspend fun getUsers() {
        withContext(Dispatchers.IO) {
            val getUsersResponse = async {
                contactApiService.getUsers(bearerToken = getAccessToken())
            }.await()
            if (getUsersResponse.code == 200) {
                val contactsDao = dataBase.contactsDao()
                async { contactsDao.insert(getUsersResponse.data.users.map { it.toContactDBO() }) }.await()
            }
        }
    }

    /**
     * @return True if contact was successfully added. False otherwise
     */
    override suspend fun addContact(contactId: Int): Boolean {
        val addContactResponse =
            contactApiService.addContact(getAccessToken(), getUserId(), ContactId(contactId))
        return addContactResponse.code == 200
    }

    /**
     * @return True if contact was successfully deleted. False otherwise
     */
    override suspend fun deleteContact(contactId: Int): Boolean {
        val deleteContactResponse =
            contactApiService.deleteContact(getUserId(), contactId, getAccessToken())
        return deleteContactResponse.code == 200
    }

    override suspend fun updateUserContacts() {
        val getUserContactsResponse =
            contactApiService.getUserContacts(getUserId(), getAccessToken())
        if (getUserContactsResponse.code == 200) {
            val userContacts = getUserContactsResponse.data.contacts.map { it.id }
            dataBase.currentUserDAO().updateUserContacts(userContacts)
        }
    }

    override suspend fun getUserContacts(): List<Contact> {
        val currentUserId = getUserId()
        val contactsIdsString = dataBase.contactsDao().getContactsIds(currentUserId)
        val contactsIdsList = contactsIdsString?.split(",")?.map { it.toInt() } ?: emptyList()
        val s = dataBase.contactsDao().getContactsByIds(contactsIdsList).map { it.toContact() }
        Log.d("AAA", "getUserContacts: ${s.size}")
        delay(5000L)
        return s
    }

    private suspend fun getUserId(): Int {
        return dataBase.currentUserDAO().getCurrentUserId()
    }

    private suspend fun getAccessToken(): String {
        return dataStoreProvider.readAccessToken().first()
    }
}
