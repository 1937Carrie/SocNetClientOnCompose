package com.dumchykov.database.di

import android.content.Context
import com.dumchykov.database.ContactsDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideContactsDatabase(@ApplicationContext context: Context): ContactsDatabase {
        return ContactsDatabase(context)
    }
}