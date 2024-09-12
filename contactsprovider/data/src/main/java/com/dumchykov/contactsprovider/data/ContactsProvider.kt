package com.dumchykov.contactsprovider.data

import com.dumchykov.contactsprovider.domain.toDomainContact
import com.dumchykov.data.Contact
import javax.inject.Inject
import com.dumchykov.contactsprovider.domain.Contact as LocalContact

class ContactsProvider @Inject constructor(
) {
    fun getContacts(): List<Contact> {
        val list = mutableListOf<LocalContact>()
        repeat(20) {
            list.add(
                LocalContact(
                    id = it,
                    name = "Name $it",
                    career = "Profession $it",
                    address = "Address $it"
                )
            )
        }
        return list.map { it.toDomainContact() }
    }
}