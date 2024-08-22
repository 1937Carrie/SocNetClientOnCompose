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
    val searchState: Boolean = false,
    val searchQuery: String = "",
)

@Serializable
@Parcelize
data class Contact(
    @SerialName("id") override val id: Int = 0,
    @SerialName("name") override val name: String = "",
    @SerialName("email") override val email: String = "",
    @SerialName("phone") override val phone: String = "",
    @SerialName("career") override val career: String = "",
    @SerialName("address") override val address: String = "",
    @SerialName("birthday") @Serializable(CalendarSerializer::class) override val birthday: Calendar = Calendar.getInstance(),
    @SerialName("facebook") override val facebook: String = "",
    @SerialName("instagram") override val instagram: String = "",
    @SerialName("twitter") override val twitter: String = "",
    @SerialName("linkedin") override val linkedin: String = "",
    @SerialName("image") override val image: String = "",
    @SerialName("created_at") @Serializable(CalendarSerializer::class) override val created_at: Calendar = Calendar.getInstance(),
    @SerialName("updated_at") @Serializable(CalendarSerializer::class) override val updated_at: Calendar = Calendar.getInstance(),
    @SerialName("isAdded") val isAdded: Boolean = false,
    @SerialName("updateUiState") val updateUiState: Boolean = false,
) : BaseContact, Parcelable {
    fun toContact(): com.dumchykov.contactsprovider.domain.Contact {
        return com.dumchykov.contactsprovider.domain.Contact(
            id = id,
            name = name.orEmpty(),
            email = email.orEmpty(),
            phone = phone.orEmpty(),
            career = career.orEmpty(),
            address = address.orEmpty(),
            birthday = birthday ?: Calendar.getInstance(),
            facebook = facebook.orEmpty(),
            instagram = instagram.orEmpty(),
            twitter = twitter.orEmpty(),
            linkedin = linkedin.orEmpty(),
            image = image.orEmpty(),
            created_at = created_at,
            updated_at = updated_at,
        )
    }

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
        id = id,
        name = name.orEmpty(),
        email = email.orEmpty(),
        phone = phone.orEmpty(),
        career = career.orEmpty(),
        address = address.orEmpty(),
        birthday = birthday ?: Calendar.getInstance(),
        facebook = facebook.orEmpty(),
        instagram = instagram.orEmpty(),
        twitter = twitter.orEmpty(),
        linkedin = linkedin.orEmpty(),
        image = image.orEmpty(),
        created_at = created_at,
        updated_at = updated_at,
    )
}
