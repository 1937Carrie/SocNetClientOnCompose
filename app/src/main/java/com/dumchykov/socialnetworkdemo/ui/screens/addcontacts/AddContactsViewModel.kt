package com.dumchykov.socialnetworkdemo.ui.screens.addcontacts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dumchykov.socialnetworkdemo.webapi.domain.ContactRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddContactsViewModel @Inject constructor(
    val contactRepository: ContactRepository,
) : ViewModel() {
    private val _addContactsState = MutableStateFlow(AddContactsState())
    val addContactsState get() = _addContactsState.asStateFlow()

    init {
        viewModelScope.launch {
            updateAddedContactsAppearance()
        }
    }

    private suspend fun updateAddedContactsAppearance() {
        val allUsers = contactRepository.getUsers().map { it.toContact() }
        val userContacts = contactRepository.getUserContacts().map { it.toContact() }
        val intersect = userContacts.map { it.id }.intersect(allUsers.map { it.id }.toSet())
        val mappedAllUsers = allUsers.map {
            if (intersect.contains(it.id)) {
                it.copy(isAdded = true)
            } else {
                it
            }
        }
        updateState { copy(allContacts = mappedAllUsers) }
    }

    private fun updateProgress(contactId: Int, startProgress: Boolean = true) {
        val contacts = addContactsState.value.allContacts.toMutableList()
        val index = contacts.indexOf(contacts.first { it.id == contactId })
        contacts[index] = contacts[index].copy(updateUiState = startProgress)
        updateState { copy(allContacts = contacts) }
    }

    fun updateState(reducer: AddContactsState.() -> AddContactsState) {
        _addContactsState.update(reducer)
    }

    fun updateContactsStateOnUi() {
        viewModelScope.launch { updateAddedContactsAppearance() }
    }

    fun addToContactList(contactId: Int) {
        viewModelScope.launch {
            updateProgress(contactId)
            contactRepository.addContact(contactId)
            updateAddedContactsAppearance()
            updateProgress(contactId, false)
        }
    }
}