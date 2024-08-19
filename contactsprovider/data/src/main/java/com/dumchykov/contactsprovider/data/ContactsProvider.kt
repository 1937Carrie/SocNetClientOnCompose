package com.dumchykov.contactsprovider.data

import com.dumchykov.contactsprovider.domain.Contact
import com.dumchykov.socialnetworkdemo.webapi.domain.ContactRepository
import jakarta.inject.Inject
import java.time.Instant

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
                    email = null,
                    phone = null,
                    birthday = Instant.MIN,
                    facebook = null,
                    instagram = null,
                    twitter = null,
                    linkedin = null,
                    image = null,
                    created_at = Instant.MIN,
                    updated_at = Instant.MIN,
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
                email = null,
                phone = null,
                birthday = Instant.MIN,
                facebook = null,
                instagram = null,
                twitter = null,
                linkedin = null,
                image = null,
                created_at = Instant.MIN,
                updated_at = Instant.MIN,
                isChecked = false,
            )
        )
    }
    return list
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