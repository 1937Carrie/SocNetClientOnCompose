package com.dumchykov.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.dumchykov.database.dao.ContactsDAO
import com.dumchykov.database.models.AuthorizedUserDBO
import com.dumchykov.database.models.ContactDBO
import com.dumchykov.database.utils.Converters

@Database(entities = [ContactDBO::class, AuthorizedUserDBO::class], version = 1)
@TypeConverters(Converters::class)
internal abstract class ContactsRoomDatabase : RoomDatabase() {
    /**
     * All contacts on the server DAO
     */
    abstract fun contactsDao(): ContactsDAO
}

class ContactsDatabase internal constructor(private val database: ContactsRoomDatabase) {
    internal val contactDao: ContactsDAO
        get() = database.contactsDao()
}

fun ContactsDatabase(applicationContext: Context): ContactsDatabase {
    val contactsRoomDatabase = Room.databaseBuilder(
        context = applicationContext,
        klass = ContactsRoomDatabase::class.java,
        name = "contacts"
    ).build()
    return ContactsDatabase(contactsRoomDatabase)
}