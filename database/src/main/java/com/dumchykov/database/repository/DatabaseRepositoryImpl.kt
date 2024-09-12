package com.dumchykov.database.repository

import com.dumchykov.data.Contact
import com.dumchykov.database.ContactsDatabase
import com.dumchykov.database.models.toAuthorizedUserDBO
import com.dumchykov.database.models.toContact
import com.dumchykov.database.models.toContactDBO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DatabaseRepositoryImpl @Inject constructor(
    private val database: ContactsDatabase,
) {
    suspend fun insertAll(contacts: List<Contact>) {
        withContext(Dispatchers.IO) {
            database.contactDao.insertAll(contacts.map { it.toContactDBO() })
        }
    }

    suspend fun insertCurrentUser(user: Contact) {
        withContext(Dispatchers.IO) {
            database.contactDao.insertCurrentUser(user.toAuthorizedUserDBO())
        }
    }

    suspend fun getAllUsers(): List<Contact> {
        return withContext(Dispatchers.IO) {
            val users = database.contactDao.getAllUsers().map { it.toContact() }
            return@withContext users
        }
    }

    suspend fun getCurrentUser(): Contact {
        return withContext(Dispatchers.IO) {
            database.contactDao.getCurrentUser().toContact()
        }
    }

    suspend fun getUserContacts(contactIdList: List<Int>): List<Contact> {
        return withContext(Dispatchers.IO) {
            database.contactDao.getUserContacts(contactIdList).map { it.toContact() }
        }
    }

    suspend fun getUserById(userId: Int): Contact {
        return withContext(Dispatchers.IO) {
            database.contactDao.getUserById(userId).toContact()
        }
    }

    suspend fun isEmpty(): Int {
        return withContext(Dispatchers.IO) {
            database.contactDao.isEmpty()
        }
    }
}