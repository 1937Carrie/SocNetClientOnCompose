package com.dumchykov.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dumchykov.database.models.ContactDBO

@Dao
interface ContactDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(contacts: List<ContactDBO>)

    @Query("SELECT * FROM contacts")
    fun getAllContacts(): List<ContactDBO>

    @Query("SELECT * FROM contacts WHERE id =:userId")
    fun getCurrentUser(userId: Int): ContactDBO
}