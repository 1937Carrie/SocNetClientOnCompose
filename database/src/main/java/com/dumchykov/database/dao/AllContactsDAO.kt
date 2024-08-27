package com.dumchykov.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.dumchykov.database.models.ContactDBO

@Dao
interface AllContactsDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(contacts: List<ContactDBO>)

    @Query("SELECT * FROM contacts")
    suspend fun getAllContacts(): List<ContactDBO>

    @Query("SELECT * FROM contacts WHERE id = :userId")
    suspend fun getUser(userId: Int): ContactDBO?

    @Query("SELECT * FROM contacts WHERE id IN (:contactIds)")
    suspend fun getContactsByIds(contactIds: List<Int>): List<ContactDBO>

    @Update
    suspend fun editUser(user: ContactDBO)
}