package com.stanislavdumchykov.socialnetworkclient.data.di

import com.stanislavdumchykov.socialnetworkclient.data.repository.UsersRepositoryImpl
import com.stanislavdumchykov.socialnetworkclient.domain.repository.UsersRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AnalyticsModule {
    @Binds
    @Singleton
    abstract fun bindUserRepository(
        usersRepository: UsersRepositoryImpl
    ): UsersRepository
}