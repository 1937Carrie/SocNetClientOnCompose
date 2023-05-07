package com.stanislavdumchykov.socialnetworkclient.data.repository

import android.content.Context
import androidx.room.Room
import com.stanislavdumchykov.socialnetworkclient.data.database.AppDatabase
import com.stanislavdumchykov.socialnetworkclient.domain.repository.DatabaseRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class DatabaseRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : DatabaseRepository {
    @Volatile
    private var instance: AppDatabase? = null

    override fun getDatabase(): AppDatabase {
        return instance ?: synchronized(this) {
            val instance = Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                "user_database"
            )
                .build()
            this.instance = instance

            instance
        }
    }
}