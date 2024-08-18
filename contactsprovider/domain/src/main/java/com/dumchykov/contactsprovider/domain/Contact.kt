package com.dumchykov.contactsprovider.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
data class Contact(
    val id: Int = 0,
    val name: String = "",
    val profession: String = "",
    val address: String = "",
    val imageHolder: Int = 0,
    val imageInternet: String = "",
    val isChecked: Boolean = false,
) : Parcelable {
    companion object {
        val previewContact = Contact(
            id = 3616,
            name = "Traci",
            profession = "Garin",
            address = "Cristi",
            imageHolder = 7961,
            imageInternet = "Kellyn",
            isChecked = true
        )
    }
}
