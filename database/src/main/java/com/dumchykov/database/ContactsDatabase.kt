package com.dumchykov.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.dumchykov.database.dao.ContactDAO
import com.dumchykov.database.models.ContactDBO
import com.dumchykov.database.utils.Converters

@Database(entities = [ContactDBO::class], version = 1)
@TypeConverters(Converters::class)
abstract class ContactsDatabase : RoomDatabase() {
    abstract fun contactsDao(): ContactDAO
}

fun ContactsDatabase(applicationContext: Context): ContactsDatabase {
    return Room.databaseBuilder(
        context = applicationContext,
        klass = ContactsDatabase::class.java,
        name = "contacts"
    ).build()
}