package com.dumchykov.socialnetworkdemo.ui.screens.addcontacts

import android.os.Parcelable
import com.dumchykov.contactsprovider.domain.BaseContact
import com.dumchykov.socialnetworkdemo.webapi.domain.models.CalendarSerializer
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.util.Calendar

data class AddContactsState(
    val allContacts: List<Contact> = emptyList(),
)

@Serializable
@Parcelize
data class Contact(
    @SerialName("id") override val id: Int = 0,
    @SerialName("name") override val name: String? = "",
    @SerialName("email") override val email: String? = "",
    @SerialName("phone") override val phone: String? = "",
    @SerialName("career") override val career: String? = "",
    @SerialName("address") override val address: String? = "",
    @SerialName("birthday") override @Serializable(CalendarSerializer::class) val birthday: Calendar? = Calendar.getInstance(),
    @SerialName("facebook") override val facebook: String? = "",
    @SerialName("instagram") override val instagram: String? = "",
    @SerialName("twitter") override val twitter: String? = "",
    @SerialName("linkedin") override val linkedin: String? = "",
    @SerialName("image") override val image: String? = "",
    @SerialName("created_at") override @Serializable(CalendarSerializer::class) val created_at: Calendar = Calendar.getInstance(),
    @SerialName("updated_at") override @Serializable(CalendarSerializer::class) val updated_at: Calendar = Calendar.getInstance(),
    @SerialName("isAdded") val isAdded: Boolean = false,
    @SerialName("updateUiState") val updateUiState: Boolean = false,
) : BaseContact, Parcelable {
    companion object {
        val sampleList: List<Contact> = mutableListOf<Contact>().apply {
            repeat(20) {
                this.add(
                    Contact(
                        id = it,
                        name = "Name $it",
                        career = "Profession $it",
                        address = "Address $it",
                        isAdded = it % 2 == 1
                    )
                )
            }
        }
    }
}

fun com.dumchykov.socialnetworkdemo.webapi.domain.models.Contact.toContact(): Contact {
    return Contact(
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


