package com.dumchykov.socialnetworkdemo.ui.screens.mycontacts.data

import android.os.Parcelable
import com.dumchykov.data.Contact
import kotlinx.parcelize.Parcelize
import java.util.Calendar

@Parcelize
data class MyContactsIndicatorContact(
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
) : Parcelable

fun Contact.toMyContactsIndicatorContact(): MyContactsIndicatorContact {
    return MyContactsIndicatorContact(
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
