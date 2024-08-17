package com.dumchykov.contactsprovider.data

import com.dumchykov.contactsprovider.domain.Contact
import jakarta.inject.Inject

class ContactsProvider @Inject constructor() {
    fun getContacts(): List<Contact> {
        val list = mutableListOf<Contact>()
        repeat(20) {
            list.add(
                Contact(
                    id = it,
                    name = "Name $it",
                    profession = "Profession $it",
                    address = "Address $it"
                )
            )
        }
        return list
    }
}