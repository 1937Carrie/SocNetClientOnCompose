package com.dumchykov.socialnetworkdemo.ui.screens.myprofile.models

import java.util.Calendar
import com.dumchykov.socialnetworkdemo.webapi.domain.models.Contact as ContactApi

data class MyProfileContact(
    val id: Int = 0,
    val name: String = "",
    val email: String = "",
    val phone: String = "",
    val career: String = "",
    val address: String = "",
    val birthday: Calendar? = null,
    val facebook: String = "",
    val instagram: String = "",
    val twitter: String = "",
    val linkedin: String = "",
    val image: String = "",
    val created_at: Calendar = Calendar.getInstance(),
    val updated_at: Calendar = Calendar.getInstance(),
    val isChecked: Boolean = false,
    val updateUiState: Boolean = false,
) {
    companion object {
        val previewContact = MyProfileContact(
            id = 2992,
            name = "Lucile Alvarado",
            email = "test@email.com",
            phone = "+3801111111",
            career = "Graphic designer",
            address = "5295 Gaylord Walks Apk. 110",
            birthday = null,
            facebook = "facebook",
            instagram = "instagram",
            twitter = "twitter",
            linkedin = "linkedin",
            image = "",
            created_at = Calendar.getInstance(),
            updated_at = Calendar.getInstance()
        )
    }
}

fun ContactApi.toMyProfileContact(): MyProfileContact {
    return MyProfileContact(
        id = this.id,
        name = this.name.orEmpty(),
        email = this.email.orEmpty(),
        phone = this.phone.orEmpty(),
        career = this.career.orEmpty(),
        address = this.address.orEmpty(),
        birthday = this.birthday,
        facebook = this.facebook.orEmpty(),
        instagram = this.instagram.orEmpty(),
        twitter = this.twitter.orEmpty(),
        linkedin = this.linkedin.orEmpty(),
        image = this.image.orEmpty(),
        created_at = this.created_at,
        updated_at = this.updated_at
    )
}
