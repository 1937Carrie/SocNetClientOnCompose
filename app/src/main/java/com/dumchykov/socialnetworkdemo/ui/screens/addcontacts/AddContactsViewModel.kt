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
        val intersect = userContacts.intersect(allUsers.toSet())
        val mappedAllUsers = allUsers.map {
            if (intersect.contains(it)) {
                it.copy(isAdded = true)
            } else {
                it
            }
        }
        updateState { copy(allContacts = mappedAllUsers) }
    }

    fun updateState(reducer: AddContactsState.() -> AddContactsState) {
        _addContactsState.update(reducer)
    }

    fun addToContactList(contactId: Int) {
        viewModelScope.launch {
            contactRepository.addContact(contactId)
            updateAddedContactsAppearance()
        }
    }
}