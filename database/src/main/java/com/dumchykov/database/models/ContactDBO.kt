package com.dumchykov.database.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.dumchykov.data.Contact
import java.util.Calendar

@Entity(tableName = "contacts")
internal data class ContactDBO(
    @PrimaryKey @ColumnInfo("id") val id: Int,
    @ColumnInfo("name") val name: String?,
    @ColumnInfo("email") val email: String?,
    @ColumnInfo("phone") val phone: String?,
    @ColumnInfo("career") val career: String?,
    @ColumnInfo("address") val address: String?,
    @ColumnInfo("birthday") val birthday: Calendar?,
    @ColumnInfo("facebook") val facebook: String?,
    @ColumnInfo("instagram") val instagram: String?,
    @ColumnInfo("twitter") val twitter: String?,
    @ColumnInfo("linkedin") val linkedin: String?,
    @ColumnInfo("image") val image: String?,
    @ColumnInfo("created_at") val createdAt: Calendar,
    @ColumnInfo("updated_at") val updatedAt: Calendar,
)

internal fun Contact.toContactDBO(): ContactDBO {
    return ContactDBO(
        id = id,
        name = name,
        email = email,
        phone = phone,
        career = career,
        address = address,
        birthday = birthday,
        facebook = facebook,
        instagram = instagram,
        twitter = twitter,
        linkedin = linkedin,
        image = image,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
}

internal fun ContactDBO.toContact(): Contact {
    return Contact(
        id = id,
        name = name.toString(),
        email = email.toString(),
        phone = phone.toString(),
        career = career.toString(),
        address = address.toString(),
        birthday = birthday,
        facebook = facebook.toString(),
        instagram = instagram.toString(),
        twitter = twitter.toString(),
        linkedin = linkedin.toString(),
        image = image,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
}