package com.dumchykov.contactsprovider.data

import com.dumchykov.contactsprovider.domain.Contact
import com.dumchykov.socialnetworkdemo.webapi.domain.ContactRepository
import jakarta.inject.Inject
import java.util.Calendar

class ContactsProvider @Inject constructor(
    val contactRepository: ContactRepository,
) {
    fun getContacts(): List<Contact> {
        val list = mutableListOf<Contact>()
        repeat(20) {
            list.add(
                Contact(
                    id = it,
                    name = "Name $it",
                    career = "Profession $it",
                    address = "Address $it",
                    isChecked = false,
                )
            )
        }
        return list
    }

    suspend fun getUserContacts(): List<Contact> {
        return contactRepository.getUserContacts().map { it.toContact() }
    }
}

fun getContacts(): List<Contact> {
    val list = mutableListOf<Contact>()
    repeat(20) {
        list.add(
            Contact(
                id = it,
                name = "Name $it",
                career = "Profession $it",
                address = "Address $it",
                isChecked = false,
            )
        )
    }
    return list
}

fun com.dumchykov.socialnetworkdemo.webapi.domain.models.Contact.toContact(): Contact {
    return Contact(
        id = this.id,
        name = this.name.orEmpty(),
        email = this.email.orEmpty(),
        phone = this.phone.orEmpty(),
        career = this.career.orEmpty(),
        address = this.address.orEmpty(),
        birthday = this.birthday ?: Calendar.getInstance(),
        facebook = this.facebook.orEmpty(),
        instagram = this.instagram.orEmpty(),
        twitter = this.twitter.orEmpty(),
        linkedin = this.linkedin.orEmpty(),
        image = this.image.orEmpty(),
        created_at = this.created_at,
        updated_at = this.updated_at
    )
}