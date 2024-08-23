package com.dumchykov.socialnetworkdemo.di

import android.content.Context
import com.dumchykov.database.ContactsDatabase
import com.dumchykov.datastore.data.DataStoreProvider
import com.dumchykov.socialnetworkdemo.BuildConfig
import com.dumchykov.socialnetworkdemo.webapi.data.ContactRepositoryImpl
import com.dumchykov.socialnetworkdemo.webapi.domain.ContactApiService
import com.dumchykov.socialnetworkdemo.webapi.domain.ContactRepository
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

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
    fun provideContext(@ApplicationContext context: Context): Context {
        return context
    }

    @Provides
    fun provideContactRepository(
        contactApiService: ContactApiService,
        dataStoreProvider: DataStoreProvider,
    ): ContactRepository {
        return ContactRepositoryImpl(contactApiService, dataStoreProvider)
        return ContactRepositoryImpl(
            contactApiService = contactApiService,
            dataStoreProvider = dataStoreProvider,
        )
    }

    @Provides
    @Singleton
    fun provideContactsDatabase(@ApplicationContext context: Context): ContactsDatabase {
        return ContactsDatabase(context)
    }
}