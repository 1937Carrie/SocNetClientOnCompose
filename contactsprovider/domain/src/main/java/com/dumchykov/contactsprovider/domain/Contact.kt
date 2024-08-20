package com.dumchykov.contactsprovider.domain

import android.os.Parcelable
import com.dumchykov.socialnetworkdemo.webapi.domain.models.InstantSerializer
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.time.Instant

@Serializable
@Parcelize
data class Contact(
    @SerialName("id") override val id: Int = 0,
    @SerialName("name") override val name: String = "",
    @SerialName("email") override val email: String = "",
    @SerialName("phone") override val phone: String = "",
    @SerialName("career") override val career: String = "",
    @SerialName("address") override val address: String = "",
    @SerialName("birthday") override @Serializable(InstantSerializer::class) val birthday: Instant = Instant.MIN,
    @SerialName("facebook") override val facebook: String = "",
    @SerialName("instagram") override val instagram: String = "",
    @SerialName("twitter") override val twitter: String = "",
    @SerialName("linkedin") override val linkedin: String = "",
    @SerialName("image") override val image: String = "",
    @SerialName("created_at") override @Serializable(InstantSerializer::class) val created_at: Instant = Instant.MIN,
    @SerialName("updated_at") override @Serializable(InstantSerializer::class) val updated_at: Instant = Instant.MIN,
    @SerialName("isChecked") val isChecked: Boolean = false,
    @SerialName("updateUiState") val updateUiState:Boolean = false,
) : Parcelable, BaseContact {
    companion object {
        val previewContact = Contact(
            id = 2992,
            name = "Lucile Alvarado",
            email = "test@email.com",
            phone = "+3801111111",
            career = "Graphic designer",
            address = "5295 Gaylord Walks Apk. 110",
            birthday = Instant.MAX,
            facebook = "facebook",
            instagram = "instagram",
            twitter = "twitter",
            linkedin = "linkedin",
            image = "",
            created_at = Instant.MIN,
            updated_at = Instant.MIN,
            isChecked = false
        )
    }
}
