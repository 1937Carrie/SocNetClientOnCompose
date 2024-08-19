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
    @SerialName("id") val id: Int = 0,
    @SerialName("name") val name: String? = "",
    @SerialName("email") val email: String? = "",
    @SerialName("phone") val phone: String? = "",
    @SerialName("career") val career: String? = "",
    @SerialName("address") val address: String? = "",
    @SerialName("birthday") @Serializable(InstantSerializer::class) val birthday: Instant? = Instant.MIN,
    @SerialName("facebook") val facebook: String? = "",
    @SerialName("instagram") val instagram: String? = "",
    @SerialName("twitter") val twitter: String? = "",
    @SerialName("linkedin") val linkedin: String? = "",
    @SerialName("image") val image: String? = "",
    @SerialName("created_at") @Serializable(InstantSerializer::class) val created_at: Instant = Instant.MIN,
    @SerialName("updated_at") @Serializable(InstantSerializer::class) val updated_at: Instant = Instant.MIN,
    @SerialName("isChecked") val isChecked: Boolean = false,
) : Parcelable {
    companion object {
        val previewContact = Contact(
            id = 2992,
            name = "Lucile Alvarado",
            email = "test@email.com",
            phone = "+3801111111",
            career = "Graphic designer",
            address = "5295 Gaylord Walks Apk. 110",
            birthday = Instant.MAX,
            facebook = null,
            instagram = null,
            twitter = null,
            linkedin = null,
            image = null,
            created_at = Instant.MIN,
            updated_at = Instant.MIN,
            isChecked = false
        )
    }
}
