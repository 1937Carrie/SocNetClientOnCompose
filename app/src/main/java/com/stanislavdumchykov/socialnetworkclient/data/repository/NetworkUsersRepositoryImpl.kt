package com.stanislavdumchykov.socialnetworkclient.data.repository

import com.stanislavdumchykov.socialnetworkclient.domain.api.RemoteData
import com.stanislavdumchykov.socialnetworkclient.domain.model.Data
import com.stanislavdumchykov.socialnetworkclient.domain.model.ServerResponse
import com.stanislavdumchykov.socialnetworkclient.domain.model.authorizeUser.AuthorizationData
import com.stanislavdumchykov.socialnetworkclient.domain.model.contacts.Contacts
import com.stanislavdumchykov.socialnetworkclient.domain.model.register.RegisterData
import com.stanislavdumchykov.socialnetworkclient.domain.model.requestModels.AuthorizeModel
import com.stanislavdumchykov.socialnetworkclient.domain.model.requestModels.ContactIdModel
import com.stanislavdumchykov.socialnetworkclient.domain.model.requestModels.EditProfileResponseData
import com.stanislavdumchykov.socialnetworkclient.domain.model.requestModels.EditProfileUser
import com.stanislavdumchykov.socialnetworkclient.domain.repository.NetworkUsersRepository
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NetworkUsersRepositoryImpl @Inject constructor(
    private val remoteData: RemoteData
) : NetworkUsersRepository {

    override suspend fun registerUser(
        email: String,
        password: String
    ): Response<ServerResponse<RegisterData>> =
        remoteData.registerUser(email, password)


    override suspend fun editUser(
        userId: Int,
        token: String,
        body: EditProfileUser
    ): Response<ServerResponse<EditProfileResponseData>> =
        remoteData.editUser(userId, token, body)

    override suspend fun authorizeUser(body: AuthorizeModel): Response<ServerResponse<AuthorizationData>> =
        remoteData.authorizeUser(body)

    override suspend fun addContact(
        userId: Int,
        token: String,
        contactId: ContactIdModel
    ): Response<ServerResponse<Data>> =
        remoteData.addContact(userId, token, contactId)

    override suspend fun getAccountUsers(
        userId: Int,
        token: String
    ): Response<ServerResponse<Contacts>> =
        remoteData.getAccountUsers(userId, token)

    override suspend fun deleteContact(
        userId: Int,
        contactId: Int,
        token: String
    ): Response<ServerResponse<Contacts>> =
        remoteData.deleteContact(userId, contactId, token)

    override suspend fun getUsers(token: String): Response<ServerResponse<Data>> =
        remoteData.getUsers(token)

}