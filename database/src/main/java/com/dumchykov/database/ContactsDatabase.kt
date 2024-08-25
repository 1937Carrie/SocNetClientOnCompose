package com.dumchykov.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.dumchykov.database.dao.AllContactsDAO
import com.dumchykov.database.dao.CurrentUserDAO
import com.dumchykov.database.models.ContactDBO
import com.dumchykov.database.models.CurrentUserDBO
import com.dumchykov.database.utils.Converters

@Database(entities = [ContactDBO::class, CurrentUserDBO::class], version = 1)
@TypeConverters(Converters::class)
abstract class ContactsDatabase : RoomDatabase() {
    /**
     * All contacts on the server DAO
     */
    abstract fun contactsDao(): AllContactsDAO

    /**
     * Authenticated user DAO
     */
    abstract fun currentUserDAO(): CurrentUserDAO
}

fun ContactsDatabase(applicationContext: Context): ContactsDatabase {
    return Room.databaseBuilder(
        context = applicationContext,
        klass = ContactsDatabase::class.java,
        name = "contacts"
    ).build()
}