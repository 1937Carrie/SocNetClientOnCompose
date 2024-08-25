package com.dumchykov.socialnetworkdemo.webapi.domain

import com.dumchykov.socialnetworkdemo.webapi.domain.models.Contact
import com.dumchykov.socialnetworkdemo.webapi.domain.models.ContactResponse
import com.dumchykov.socialnetworkdemo.webapi.domain.models.SingleUserResponse

interface ContactRepository {
    suspend fun addContact(contactId: Int): Boolean
    suspend fun authorize(email: String, password: String): Boolean
    suspend fun deleteContact(contactId: Int): Boolean
    suspend fun editUser(user: Contact)
    suspend fun getUser(): Contact
    suspend fun getUserContacts(): List<Contact>
    suspend fun getUsers()
    suspend fun refreshToken()
    suspend fun register(email: String, password: String, saveCredentials: Boolean = false)
    suspend fun updateUserContacts()
}