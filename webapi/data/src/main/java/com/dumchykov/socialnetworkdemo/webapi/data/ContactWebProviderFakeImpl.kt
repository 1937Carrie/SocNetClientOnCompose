package com.dumchykov.socialnetworkdemo.webapi.data

import com.dumchykov.socialnetworkdemo.webapi.domain.ContactRepository
import com.dumchykov.socialnetworkdemo.webapi.domain.models.Contact
import com.dumchykov.socialnetworkdemo.webapi.domain.models.ContactResponse
import com.dumchykov.socialnetworkdemo.webapi.domain.models.SingleUserResponse
import java.time.Instant

class ContactWebProviderFakeImpl : ContactRepository {
    override suspend fun register(email: String, password: String, saveCredentials: Boolean) {}
    override suspend fun authorize(
        email: String,
        password: String,
    ): ContactResponse<SingleUserResponse> {
        return ContactResponse(
            "",
            200,
            "",
            SingleUserResponse(
                Contact(
                    id = 1656,
                    name = null,
                    email = null,
                    phone = null,
                    career = null,
                    address = null,
                    birthday = null,
                    facebook = null,
                    instagram = null,
                    twitter = null,
                    linkedin = null,
                    image = null,
                    created_at = Instant.MIN,
                    updated_at = Instant.MIN,
                ), "", ""
            )
        )
    }

    override suspend fun refreshToken() {}
    override suspend fun getUser() {}
    override suspend fun editUser(user: Contact) {}
    override suspend fun getUsers() {}
    override suspend fun addContact() {}
    override suspend fun deleteContact() {}
    override suspend fun getUserContacts() {}
}