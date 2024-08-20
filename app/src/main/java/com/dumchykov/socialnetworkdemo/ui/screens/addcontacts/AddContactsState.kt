package com.dumchykov.socialnetworkdemo.ui.screens.addcontacts

import android.os.Parcelable
import com.dumchykov.contactsprovider.domain.BaseContact
import com.dumchykov.socialnetworkdemo.webapi.domain.models.InstantSerializer
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.time.Instant

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
    @SerialName("birthday") override @Serializable(InstantSerializer::class) val birthday: Instant? = Instant.MIN,
    @SerialName("facebook") override val facebook: String? = "",
    @SerialName("instagram") override val instagram: String? = "",
    @SerialName("twitter") override val twitter: String? = "",
    @SerialName("linkedin") override val linkedin: String? = "",
    @SerialName("image") override val image: String? = "",
    @SerialName("created_at") override @Serializable(InstantSerializer::class) val created_at: Instant = Instant.MIN,
    @SerialName("updated_at") override @Serializable(InstantSerializer::class) val updated_at: Instant = Instant.MIN,
    @SerialName("isAdded") val isAdded: Boolean = false,
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


