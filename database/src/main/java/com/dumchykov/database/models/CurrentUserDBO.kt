package com.dumchykov.database.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "current_user")
data class CurrentUserDBO(
    @PrimaryKey @ColumnInfo("id") val id: Int,
    @ColumnInfo("currentUserId") val currentUserId: Int,
    @ColumnInfo("contactsIds") val contactsIds: List<Int>,
)
