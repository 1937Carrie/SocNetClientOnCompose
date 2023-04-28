package com.stanislavdumchykov.socialnetworkclient.domain.repository

import kotlinx.coroutines.flow.Flow

interface StorageRepository {
    val getEmailToken: Flow<String>
    val getPasswordToken: Flow<String>
    val getToken: Flow<String>

    suspend fun saveString(key: String, value: String)
}