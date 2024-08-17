package com.dumchykov.socialnetworkdemo.webapi.data

import com.dumchykov.socialnetworkdemo.webapi.domain.ContactApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit

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

val retrofitService: ContactApiService by lazy {
    retrofit.create(ContactApiService::class.java)
}