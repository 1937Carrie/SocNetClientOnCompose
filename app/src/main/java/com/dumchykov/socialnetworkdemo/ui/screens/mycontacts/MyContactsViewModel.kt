package com.dumchykov.socialnetworkdemo.ui.screens.mycontacts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dumchykov.contactsprovider.data.ContactsProvider
import com.dumchykov.contactsprovider.domain.Contact
import com.dumchykov.socialnetworkdemo.webapi.domain.ContactRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyContactsViewModel @Inject constructor(
    private val contactsProvider: ContactsProvider,
    private val contactRepository: ContactRepository,
) : ViewModel() {
    private val _myContactsState = MutableStateFlow(MyContactsState())
    val myContactsState get() = _myContactsState.asStateFlow()

    init {
        viewModelScope.launch {
            updateContactsAppearance()
        }
    }

    private fun updateMultiselectState(): Boolean {
        return myContactsState.value.contacts.count { it.isChecked } != 0
    }

    private suspend fun updateContactsAppearance() {
        val userContacts = contactsProvider.getUserContacts()
        updateState { copy(contacts = userContacts) }
    }

    fun updateState(reducer: MyContactsState.() -> MyContactsState) {
        _myContactsState.update(reducer)
    }

    fun addContact(contact: Contact) {
        viewModelScope.launch {
            val onAddContactResult = contactRepository.addContact(contact.id)
            if (onAddContactResult) {
                val userContacts = contactsProvider.getUserContacts()
                updateState { copy(contacts = userContacts) }
            }
        }
    }

    fun deleteContact(contactId: Int) {
        viewModelScope.launch {
            val onDeleteResult = contactRepository.deleteContact(contactId)
            if (onDeleteResult) {
                updateContactsAppearance()
            }
        }
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

    fun deleteSelected() {
        val contacts = myContactsState.value.contacts.toMutableList()
        contacts.filter { it.isChecked }.forEach { deleteContact(it.id) }
        contacts.removeIf { it.isChecked }
        updateState { copy(contacts = contacts) }
        updateState { copy(isMultiselect = updateMultiselectState()) }
    }

    fun updateListOnEnter() {
        viewModelScope.launch {
            updateContactsAppearance()
        }
    }
}
