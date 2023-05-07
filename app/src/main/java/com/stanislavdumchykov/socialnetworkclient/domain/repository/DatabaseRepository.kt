package com.stanislavdumchykov.socialnetworkclient.domain.repository

import com.stanislavdumchykov.socialnetworkclient.data.database.AppDatabase

interface DatabaseRepository {
    fun getDatabase(): AppDatabase
}