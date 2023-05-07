package com.stanislavdumchykov.socialnetworkclient.data.database.user

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    @PrimaryKey @ColumnInfo(name = "id") val id: Int = 0,
    @ColumnInfo(name = "server_id") val serverId: Int = 0,
    @ColumnInfo(name = "name") val name: String? = "",
    @ColumnInfo(name = "career") val career: String? = "",
    @ColumnInfo(name = "address") val address: String? = "",
    @ColumnInfo(name = "birthday") val birthday: String? = "",
    @ColumnInfo(name = "facebook") val facebook: String? = "",
    @ColumnInfo(name = "instagram") val instagram: String? = "",
    @ColumnInfo(name = "linkedin") val linkedin: String? = "",
    @ColumnInfo(name = "twitter") val twitter: String? = "",
    @ColumnInfo(name = "image") val image: String? = "",
    @ColumnInfo(name = "phone") val phone: String? = "",
    @ColumnInfo(name = "email") val email: String? = "",
    @ColumnInfo(name = "created_at") val created_at: String? = "",
    @ColumnInfo(name = "updated_at") val updated_at: String? = "",
){
    fun User.toUser():com.stanislavdumchykov.socialnetworkclient.domain.model.User{
        return com.stanislavdumchykov.socialnetworkclient.domain.model.User(
            id = serverId,
            name = name,
            career = career,
            address = address,
            birthday = birthday,
            facebook = facebook,
            instagram = instagram,
            linkedin = linkedin,
            twitter = twitter,
            image = image,
            phone = phone,
            email = email,
            created_at = created_at,
            updated_at = updated_at,

        )
    }
}