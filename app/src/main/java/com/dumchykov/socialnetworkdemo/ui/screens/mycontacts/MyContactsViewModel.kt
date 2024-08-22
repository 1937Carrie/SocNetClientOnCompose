package com.dumchykov.socialnetworkdemo.ui.screens.mycontacts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dumchykov.contactsprovider.data.ContactsProvider
import com.dumchykov.contactsprovider.domain.Contact
import com.dumchykov.socialnetworkdemo.webapi.domain.ContactRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
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

    private val _deleteContactState = MutableSharedFlow<Contact>()
    val deleteContactState get() = _deleteContactState.asSharedFlow()

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

    fun deleteContact(contactId: Int, singleDelete: Boolean = true) {
        viewModelScope.launch {
            updateProgress(contactId)
            val onDeleteResult = contactRepository.deleteContact(contactId)
            if (onDeleteResult) {
                val contact = getContactById(contactId)
                updateContactsAppearance()
                if (singleDelete) {
                    _deleteContactState.emit(contact)
                } else {
                    updateState { copy(isMultiselect = updateMultiselectState()) }
                }
            } else {
                updateProgress(contactId, false)
            }
        }
    }

    private fun updateProgress(contactId: Int, startProgress: Boolean = true) {
        val contacts = myContactsState.value.contacts.toMutableList()
        val index = contacts.indexOf(contacts.first { it.id == contactId })
        contacts[index] = contacts[index].copy(updateUiState = startProgress)
        updateState { copy(contacts = contacts) }
    }

    private fun getContactById(contactId: Int): Contact {
        val contacts = myContactsState.value.contacts.toMutableList()
        val index = contacts.indexOf(contacts.first { it.id == contactId })
        return contacts[index]
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
        contacts.filter { it.isChecked }.forEach { deleteContact(it.id, false) }
    }

    fun updateListOnEnter() {
        viewModelScope.launch {
            updateContactsAppearance()
        }
    }
}
