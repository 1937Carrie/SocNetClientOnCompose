package com.dumchykov.database.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Calendar

@Entity(tableName = "contacts")
data class ContactDBO(
    @PrimaryKey @ColumnInfo("id") val id: Int,
    @ColumnInfo("name") val name: String,
    @ColumnInfo("email") val email: String,
    @ColumnInfo("phone") val phone: String,
    @ColumnInfo("career") val career: String,
    @ColumnInfo("address") val address: String,
    @ColumnInfo("birthday") val birthday: Calendar?,
    @ColumnInfo("facebook") val facebook: String,
    @ColumnInfo("instagram") val instagram: String,
    @ColumnInfo("twitter") val twitter: String,
    @ColumnInfo("linkedin") val linkedin: String,
    @ColumnInfo("image") val image: String,
    @ColumnInfo("created_at") val created_at: Calendar,
    @ColumnInfo("updated_at") val updated_at: Calendar,
)