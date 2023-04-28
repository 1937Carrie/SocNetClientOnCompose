package com.stanislavdumchykov.socialnetworkclient.domain.api

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
import javax.inject.Inject

class RemoteData @Inject constructor(private val serverApi: ServerApi) {

    suspend fun registerUser(
        email: String,
        password: String
    ): Response<ServerResponse<RegisterData>> =
        serverApi.registerUser(email, password)

    suspend fun editUser(
        userId: Int,
        token: String,
        body: EditProfileUser
    ): Response<ServerResponse<EditProfileResponseData>> =
        serverApi.editUser(userId, token, body)

    suspend fun authorizeUser(body: AuthorizeModel): Response<ServerResponse<AuthorizationData>> =
        serverApi.authorizeUser(body)

    suspend fun addContact(
        userId: Int,
        token: String,
        contactId: ContactIdModel
    ): Response<ServerResponse<Data>> =
        serverApi.addContact(userId, token, contactId)

    suspend fun getAccountUsers(userId: Int, token: String): Response<ServerResponse<Contacts>> =
        serverApi.getAccountUsers(userId, token)

    suspend fun deleteContact(
        userId: Int,
        contactId: Int,
        token: String
    ): Response<ServerResponse<Contacts>> =
        serverApi.deleteContact(userId, contactId, token)

    suspend fun getUsers(token: String): Response<ServerResponse<Data>> =
        serverApi.getUsers(token)
}