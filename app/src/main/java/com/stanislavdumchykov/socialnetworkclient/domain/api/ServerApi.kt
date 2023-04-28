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
import retrofit2.http.*

interface ServerApi {
    @POST("users")
    @Headers("Content-type: multipart/form-data")
    suspend fun registerUser(
        @Field("email") email: String,
        @Field("password") password: String
    ): Response<ServerResponse<RegisterData>>

    @PUT("users/{userID}")
    @Headers("Content-type: application/json")
    suspend fun editUser(
        @Path("userID") userId: Int,
        @Header("Authorization") token: String,
        @Body body: EditProfileUser
    ): Response<ServerResponse<EditProfileResponseData>>

    @POST("login")
    @Headers("Content-type: application/json")
    suspend fun authorizeUser(
        @Body body: AuthorizeModel
    ): Response<ServerResponse<AuthorizationData>>

    @PUT("users/{userID}/contacts")
    @Headers("Content-type: application/json")
    suspend fun addContact(
        @Path("userID") userId: Int,
        @Header("Authorization") token: String,
        @Body() contactId: ContactIdModel
    ): Response<ServerResponse<Data>>

    @GET("users/{userID}/contacts")
    suspend fun getAccountUsers(
        @Path("userID") userId: Int,
        @Header("Authorization") token: String
    ): Response<ServerResponse<Contacts>>

    @DELETE("users/{userId}/contacts/{contactId}")
    suspend fun deleteContact(
        @Path("userId") userId: Int,
        @Path("contactId") contactId: Int,
        @Header("Authorization") token: String
    ): Response<ServerResponse<Contacts>>

    @GET("users")
    suspend fun getUsers(@Header("Authorization") token: String): Response<ServerResponse<Data>>

}