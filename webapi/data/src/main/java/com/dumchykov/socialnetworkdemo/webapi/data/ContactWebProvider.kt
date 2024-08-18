package com.dumchykov.socialnetworkdemo.webapi.data

import com.dumchykov.datastore.data.DataStoreProvider
import com.dumchykov.socialnetworkdemo.webapi.domain.ContactApiService
import com.dumchykov.socialnetworkdemo.webapi.domain.ContactRepository
import com.dumchykov.socialnetworkdemo.webapi.domain.models.ContactResponse
import com.dumchykov.socialnetworkdemo.webapi.domain.models.EmailPassword
import com.dumchykov.socialnetworkdemo.webapi.domain.models.SingleUserResponse
import jakarta.inject.Inject

class ContactWebProvider @Inject constructor(
    private val contactApiService: ContactApiService,
    private val dataStoreProvider: DataStoreProvider,
): ContactRepository {
    override suspend fun register(email: String, password: String, saveCredentials: Boolean) {
        try {
            val registerResponse = contactApiService.register(email, password)
            if (saveCredentials) {
                dataStoreProvider.saveCredentials(email, password)
            }

        } catch (e: Exception) {

        }
    }

    override suspend fun authorize(email: String, password: String): ContactResponse<SingleUserResponse> {
        return contactApiService.authorize(EmailPassword(email, password))
    }

    override suspend fun refreshToken() {}
    override suspend fun getUser() {}
    override suspend fun editUser() {}
    override suspend fun getUsers() {}
    override suspend fun addContact() {}
    override suspend fun deleteContact() {}
    override suspend fun getUserContacts() {}
}