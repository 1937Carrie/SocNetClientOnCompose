package com.dumchykov.socialnetworkdemo.webapi.domain

import com.dumchykov.socialnetworkdemo.webapi.domain.models.ApiContact
import com.dumchykov.socialnetworkdemo.webapi.domain.models.ContactId
import com.dumchykov.socialnetworkdemo.webapi.domain.models.ContactResponse
import com.dumchykov.socialnetworkdemo.webapi.domain.models.EditUserResponse
import com.dumchykov.socialnetworkdemo.webapi.domain.models.EmailPassword
import com.dumchykov.socialnetworkdemo.webapi.domain.models.MultipleContactResponse
import com.dumchykov.socialnetworkdemo.webapi.domain.models.MultipleUserResponse
import com.dumchykov.socialnetworkdemo.webapi.domain.models.SingleUserResponse
import com.dumchykov.socialnetworkdemo.webapi.domain.models.TokenResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ContactApiService {
    @FormUrlEncoded
    @Headers("Content-Type: multipart/form-data")
    @POST("users")
    suspend fun register(
        @Field("email") email: String,
        @Field("password") password: String,
    ): ContactResponse<SingleUserResponse>

    @POST("login")
    suspend fun authorize(@Body emailPassword: EmailPassword): ContactResponse<SingleUserResponse>

    @POST("refresh")
    suspend fun refreshToken(@Header("RefreshToken") refreshToken: String): ContactResponse<TokenResponse>

    // TODO: Why http 403 forbidden?
    @GET("users/{userId}")
    suspend fun getUser(
        @Path("userId") userId: Int,
        @Header("Authorization") bearerToken: String,
    ): ContactResponse<SingleUserResponse>

    @Headers("Content-Type: application/json")
    @PUT("users/{userId}")
    suspend fun editUser(
        @Path("userId") userId: Int,
        @Header("Authorization") bearerToken: String,
        @Body user: ApiContact,
    ): ContactResponse<EditUserResponse>

    @GET("users")
    suspend fun getUsers(@Header("Authorization") bearerToken: String): ContactResponse<MultipleUserResponse>

    @Headers("Content-type: application/json")
    @PUT("users/{userId}/contacts")
    suspend fun addContact(
        @Header("Authorization") bearerToken: String,
        @Path("userId") userId: Int,
        @Body contactId: ContactId,
    ): ContactResponse<MultipleContactResponse>

    @DELETE("users/{userId}/contacts/{contactId}")
    suspend fun deleteContact(
        @Path("userId") userId: Int,
        @Path("contactId") contactId: Int,
        @Header("Authorization") bearerToken: String,
    ): ContactResponse<MultipleContactResponse>

    @GET("users/{userId}/contacts")
    suspend fun getUserContacts(
        @Path("userId") userId: Int,
        @Header("Authorization") bearerToken: String,
    ): ContactResponse<MultipleContactResponse>
}
