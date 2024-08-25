package com.dumchykov.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dumchykov.database.models.CurrentUserDBO

@Dao
interface CurrentUserDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(contacts: CurrentUserDBO)

    @Query("SELECT currentUserId FROM current_user")
    suspend fun getCurrentUserId():Int

    @Query("SELECT * FROM current_user")
    suspend fun getAllContacts(): CurrentUserDBO

    @Query("UPDATE current_user SET contactsIds = :updatedContactsIds")
    suspend fun updateUserContacts(updatedContactsIds: List<Int>)
}