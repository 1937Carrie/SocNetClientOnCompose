package com.dumchykov.socialnetworkdemo.webapi.data

import com.dumchykov.datastore.data.DataStoreProvider
import com.dumchykov.socialnetworkdemo.webapi.domain.models.ContactResponse
import com.dumchykov.socialnetworkdemo.webapi.domain.models.EmailPassword
import com.dumchykov.socialnetworkdemo.webapi.domain.models.SingleUserResponse
import com.dumchykov.socialnetworkdemo.webapi.domain.ContactApiService

class ContactWebProvider(
    private val contactApiService: ContactApiService,
    private val dataStoreProvider: DataStoreProvider,
) {
    suspend fun register(email: String, password: String, saveCredentials: Boolean = false) {
        try {
            val registerResponse = contactApiService.register(email, password)
            if (saveCredentials) {
                dataStoreProvider.saveCredentials(email, password)
            }

        } catch (e: Exception) {

        }
    }

    suspend fun authorize(email: String, password: String): ContactResponse<SingleUserResponse> {
        return contactApiService.authorize(EmailPassword(email, password))
    }

    suspend fun refreshToken() {}
    suspend fun getUser() {}
    suspend fun editUser() {}
    suspend fun getUsers() {}
    suspend fun addContact() {}
    suspend fun deleteContact() {}
    suspend fun getUserContacts() {}
}