package com.dumchykov.socialnetworkdemo.webapi.domain

import com.dumchykov.socialnetworkdemo.webapi.domain.models.Contact
import com.dumchykov.socialnetworkdemo.webapi.domain.models.ContactResponse
import com.dumchykov.socialnetworkdemo.webapi.domain.models.SingleUserResponse

interface ContactRepository {
    suspend fun register(email: String, password: String, saveCredentials: Boolean = false)
    suspend fun authorize(email: String, password: String): ContactResponse<SingleUserResponse>
    suspend fun refreshToken()
    suspend fun getUser()
    suspend fun editUser(user: Contact)
    suspend fun getUsers()
    suspend fun addContact()
    suspend fun deleteContact()
    suspend fun getUserContacts()
}