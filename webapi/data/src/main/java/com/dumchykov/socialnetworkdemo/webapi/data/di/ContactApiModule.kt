package com.dumchykov.socialnetworkdemo.webapi.data.di

import com.dumchykov.database.repository.DatabaseRepositoryImpl
import com.dumchykov.datastore.data.DataStoreProvider
import com.dumchykov.socialnetworkdemo.webapi.data.BuildConfig
import com.dumchykov.socialnetworkdemo.webapi.data.ContactRepositoryImpl
import com.dumchykov.socialnetworkdemo.webapi.domain.ContactApiService
import com.dumchykov.socialnetworkdemo.webapi.domain.ContactRepository
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ContactApiModule {
    @Provides
    @Singleton
    fun provideWebApi(client: OkHttpClient): ContactApiService {
        return Retrofit.Builder()
            .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
            .baseUrl(BuildConfig.BASE_URL)
            .client(client)
            .build()
            .create()
    }

    @Provides
    fun provideContactRepository(
        contactApiService: ContactApiService,
        databaseRepository: DatabaseRepositoryImpl,
        dataStoreProvider: DataStoreProvider,
    ): ContactRepository {
        return ContactRepositoryImpl(
            contactApiService = contactApiService,
            databaseRepository = databaseRepository,
            dataStoreProvider = dataStoreProvider
        )
    }
}