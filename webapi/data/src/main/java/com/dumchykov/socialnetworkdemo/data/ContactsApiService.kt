package com.dumchykov.socialnetworkdemo.data

import com.dumchykov.socialnetworkdemo.data.models.Contact
import com.dumchykov.socialnetworkdemo.data.models.ContactId
import com.dumchykov.socialnetworkdemo.data.models.ContactResponse
import com.dumchykov.socialnetworkdemo.data.models.EmailPassword
import com.dumchykov.socialnetworkdemo.data.models.MultipleContactResponse
import com.dumchykov.socialnetworkdemo.data.models.MultipleUserResponse
import com.dumchykov.socialnetworkdemo.data.models.SingleUserResponse
import com.dumchykov.socialnetworkdemo.data.models.TokenResponse
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
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

private val baseUrl = "http://178.63.9.114:7777/api/"

private val retrofit: Retrofit = Retrofit.Builder()
    .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
    .baseUrl(baseUrl)
    .client(
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .build()
    )
    .build()

val retrofitService: ContactsApiService by lazy {
    retrofit.create(ContactsApiService::class.java)
}

interface ContactsApiService {
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
        @Body user: Contact,
    ): ContactResponse<SingleUserResponse>

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
