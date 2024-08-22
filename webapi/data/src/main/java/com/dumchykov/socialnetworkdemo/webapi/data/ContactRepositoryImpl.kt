package com.dumchykov.socialnetworkdemo.webapi.data

import com.dumchykov.datastore.data.DataStoreProvider
import com.dumchykov.socialnetworkdemo.webapi.domain.ContactApiService
import com.dumchykov.socialnetworkdemo.webapi.domain.ContactRepository
import com.dumchykov.socialnetworkdemo.webapi.domain.models.Contact
import com.dumchykov.socialnetworkdemo.webapi.domain.models.ContactId
import com.dumchykov.socialnetworkdemo.webapi.domain.models.ContactResponse
import com.dumchykov.socialnetworkdemo.webapi.domain.models.EmailPassword
import com.dumchykov.socialnetworkdemo.webapi.domain.models.SingleUserResponse
import kotlinx.coroutines.flow.first

class ContactRepositoryImpl(
    private val contactApiService: ContactApiService,
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
    ): ContactResponse<SingleUserResponse> {
        return contactApiService.authorize(EmailPassword(email, password))
    }

    override suspend fun refreshToken() {}
    override suspend fun getUser() {}
    override suspend fun editUser(user: Contact) {
        val userId = getUserId()
        val token = getAccessToken()
        val editedUserResponse = contactApiService.editUser(
            userId = userId,
            bearerToken = token,
            user = user
        )
        if (editedUserResponse.code == 200) {
            dataStoreProvider.saveContact(editedUserResponse.data.user)
        }
    }

    override suspend fun getUsers(): List<Contact> {
        val getUsersResponse =
            contactApiService.getUsers(bearerToken = getAccessToken())
        return if (getUsersResponse.code == 200) {
            getUsersResponse.data.users
        } else emptyList()
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

    override suspend fun getUserContacts(): List<Contact> {
        val getUserContactsResponse =
            contactApiService.getUserContacts(getUserId(), getAccessToken())
        return if (getUserContactsResponse.code == 200) {
            getUserContactsResponse.data.contacts
        } else emptyList()
    }

    private suspend fun getUserId(): Int {
        return dataStoreProvider.getContact().first().id
    }

    private suspend fun getAccessToken(): String {
        return dataStoreProvider.readAccessToken().first()
    }
}
