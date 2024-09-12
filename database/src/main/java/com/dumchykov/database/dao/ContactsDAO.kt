package com.dumchykov.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dumchykov.database.models.AuthorizedUserDBO
import com.dumchykov.database.models.ContactDBO

@Dao
internal interface ContactsDAO {
    @Insert(entity = ContactDBO::class, onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(contacts: List<ContactDBO>)

    @Insert(entity = AuthorizedUserDBO::class, onConflict = OnConflictStrategy.REPLACE)
    fun insertCurrentUser(user: AuthorizedUserDBO)

    @Query("SELECT * FROM contacts")
    fun getAllUsers(): List<ContactDBO>

    @Query("SELECT * FROM current_user LIMIT 1")
    fun getCurrentUser(): AuthorizedUserDBO

    @Query("SELECT * FROM contacts WHERE contacts.id IN (:contactIdList)")
    fun getUserContacts(contactIdList: List<Int>): List<ContactDBO>

    @Query("SELECT * FROM contacts WHERE contacts.id = :userId")
    fun getUserById(userId: Int): ContactDBO

    /**
     * Checks count of entries in the table and return that value
     *
     * @return 0 if table is empty, otherwise 1.
     */
    @Query("SELECT CASE WHEN COUNT(*) = 0 THEN 0 ELSE 1 END FROM contacts")
    fun isEmpty(): Int
}