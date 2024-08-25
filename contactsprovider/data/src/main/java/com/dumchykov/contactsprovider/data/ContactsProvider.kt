package com.dumchykov.contactsprovider.data

import com.dumchykov.contactsprovider.domain.Contact
import javax.inject.Inject

class ContactsProvider @Inject constructor(
) {
    fun getContacts(): List<Contact> {
        val list = mutableListOf<Contact>()
        repeat(20) {
            list.add(
                Contact(
                    id = it,
                    name = "Name $it",
                    career = "Profession $it",
                    address = "Address $it"
                )
            )
        }
        return list
    }
}