package com.stanislavdumchykov.socialnetworkclient.domain.repository

import com.stanislavdumchykov.socialnetworkclient.domain.model.Data
import com.stanislavdumchykov.socialnetworkclient.domain.model.ServerResponse
import com.stanislavdumchykov.socialnetworkclient.domain.model.authorizeUser.AuthorizationData
import com.stanislavdumchykov.socialnetworkclient.domain.model.contacts.Contacts
import com.stanislavdumchykov.socialnetworkclient.domain.model.register.RegisterData
import com.stanislavdumchykov.socialnetworkclient.domain.model.requestModels.AuthorizeModel
import com.stanislavdumchykov.socialnetworkclient.domain.model.requestModels.ContactIdModel
import com.stanislavdumchykov.socialnetworkclient.domain.model.requestModels.EditProfileResponseData
import com.stanislavdumchykov.socialnetworkclient.domain.model.requestModels.EditProfileUser
import retrofit2.Response

interface NetworkUsersRepository {
    suspend fun registerUser(
        email: String,
        password: String
    ): Response<ServerResponse<RegisterData>>

    suspend fun editUser(
        userId: Int,
        token: String,
        body: EditProfileUser
    ): Response<ServerResponse<EditProfileResponseData>>

    suspend fun authorizeUser(body: AuthorizeModel): Response<ServerResponse<AuthorizationData>>

    suspend fun addContact(
        userId: Int,
        token: String,
        contactId: ContactIdModel
    ): Response<ServerResponse<Data>>

    suspend fun getAccountUsers(
        userId: Int,
        token: String
    ): Response<ServerResponse<Contacts>>

    suspend fun deleteContact(
        userId: Int,
        contactId: Int,
        token: String
    ): Response<ServerResponse<Contacts>>

    suspend fun getUsers(token: String): Response<ServerResponse<Data>>
}