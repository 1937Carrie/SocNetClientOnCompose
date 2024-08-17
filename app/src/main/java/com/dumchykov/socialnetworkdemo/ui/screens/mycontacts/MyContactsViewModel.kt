package com.dumchykov.socialnetworkdemo.ui.screens.mycontacts

import androidx.lifecycle.ViewModel
import com.dumchykov.contactsprovider.data.ContactsProvider
import com.dumchykov.contactsprovider.domain.Contact
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class MyContactsViewModel @Inject constructor(
    private val contactsProvider: ContactsProvider,
) : ViewModel() {
    private val _myContactsState = MutableStateFlow(MyContactsState())
    val myContactsState get() = _myContactsState.asStateFlow()

    init {
        updateState { copy(contacts = contactsProvider.getContacts()) }
    }

    fun updateState(reducer: MyContactsState.() -> MyContactsState) {
        _myContactsState.update(reducer)
    }

    fun addContact(contact: Contact) {
        val tempContacts = myContactsState.value.contacts.toMutableList()
        tempContacts.add(contact)
        updateState { copy(contacts = tempContacts) }
    }

    fun addContact(index: Int, contact: Contact) {
        val tempContacts = myContactsState.value.contacts.toMutableList()
        tempContacts.add(index, contact)
        updateState { copy(contacts = tempContacts) }
    }

    fun deleteContact(contact: Contact) {
        val tempContacts = myContactsState.value.contacts.toMutableList()
        tempContacts -= contact
        updateState { copy(contacts = tempContacts) }
    }

    fun changeContactSelectedState(contact: Contact) {
        val searchedContact = myContactsState.value.contacts.first { it == contact }
        val searchedIndex = myContactsState.value.contacts.indexOf(searchedContact)
        val updatedContacts = myContactsState
            .value
            .contacts
            .toMutableList()
        updatedContacts[searchedIndex] =
            searchedContact.copy(isChecked = searchedContact.isChecked.not())
        updateState { copy(contacts = updatedContacts) }
        updateState { copy(isMultiselect = updateMultiselectState()) }
    }

    private fun updateMultiselectState(): Boolean {
        return myContactsState.value.contacts.count { it.isChecked } != 0
    }

    fun deleteSelected() {
        val contacts = myContactsState.value.contacts.toMutableList()
        contacts.removeIf { it.isChecked }
        updateState { copy(contacts = contacts) }
        updateState { copy(isMultiselect = updateMultiselectState()) }
    }
}
