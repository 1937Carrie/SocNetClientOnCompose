package com.dumchykov.socialnetworkdemo.ui.screens.myprofile.data

import com.dumchykov.data.Contact
import java.util.Calendar

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
    val image: String? = null,
    val createdAt: Calendar = Calendar.getInstance(),
    val updatedAt: Calendar = Calendar.getInstance(),
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
            image = null,
            createdAt = Calendar.getInstance(),
            updatedAt = Calendar.getInstance()
        )
    }
}

fun Contact.toMyProfileContact(): MyProfileContact{
    return MyProfileContact(
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
        updatedAt = updatedAt,
    )
}