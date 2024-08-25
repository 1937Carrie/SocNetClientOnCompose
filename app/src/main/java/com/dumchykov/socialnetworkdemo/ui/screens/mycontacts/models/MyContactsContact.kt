package com.dumchykov.socialnetworkdemo.ui.screens.mycontacts.models

import com.dumchykov.contactsprovider.domain.Contact
import com.dumchykov.socialnetworkdemo.util.BaseContact
import kotlinx.parcelize.Parcelize
import java.util.Calendar
import com.dumchykov.socialnetworkdemo.webapi.domain.models.Contact as ContactAPI

@Parcelize
data class MyContactsContact(
    override val id: Int = 0,
    override val name: String = "",
    override val email: String = "",
    override val phone: String = "",
    override val career: String = "",
    override val address: String = "",
    override val birthday: Calendar? = null,
    override val facebook: String = "",
    override val instagram: String = "",
    override val twitter: String = "",
    override val linkedin: String = "",
    override val image: String = "",
    override val created_at: Calendar = Calendar.getInstance(),
    override val updated_at: Calendar = Calendar.getInstance(),
    val isChecked: Boolean = false,
    val updateUiState: Boolean = false,
) : BaseContact

fun ContactAPI.toMyContactsContact(): MyContactsContact {
    return MyContactsContact(
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

fun Contact.toMyContactsContact(): MyContactsContact {
    return MyContactsContact(
        id = this.id,
        name = this.name,
        email = this.email,
        phone = this.phone,
        career = this.career,
        address = this.address,
        birthday = this.birthday,
        facebook = this.facebook,
        instagram = this.instagram,
        twitter = this.twitter,
        linkedin = this.linkedin,
        image = this.image,
        created_at = this.created_at,
        updated_at = this.updated_at
    )
}