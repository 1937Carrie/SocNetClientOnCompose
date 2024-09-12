package com.dumchykov.socialnetworkdemo.ui.screens.addcontacts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dumchykov.data.Contact
import com.dumchykov.database.repository.DatabaseRepositoryImpl
import com.dumchykov.socialnetworkdemo.ui.screens.addcontacts.data.AddContactsContact
import com.dumchykov.socialnetworkdemo.ui.screens.addcontacts.data.toAddContactsContact
import com.dumchykov.socialnetworkdemo.webapi.domain.ContactRepository
import com.dumchykov.socialnetworkdemo.webapi.domain.ResponseState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddContactsViewModel @Inject constructor(
    private val contactRepository: ContactRepository,
    private val databaseRepository: DatabaseRepositoryImpl,
) : ViewModel() {
    private val _addContactsState = MutableStateFlow(AddContactsState())
    val addContactsState get() = _addContactsState.asStateFlow()

    private val _addedContactState = MutableSharedFlow<AddContactsContact>()
    val addedContactState get() = _addedContactState.asSharedFlow()

    private fun updateProgress(contactId: Int, startProgress: Boolean = true) {
        val contacts = addContactsState.value.allContacts.toMutableList()
        val index = contacts.indexOf(contacts.first { it.id == contactId })
        contacts[index] = contacts[index].copy(updateUiState = startProgress)
        updateState { copy(allContacts = contacts) }
    }

    private fun updateAddedContactsAppearance() {
        viewModelScope.launch {
            val allUsers = databaseRepository.getAllUsers()
            val userContactsResponse = contactRepository.getUserContacts()
            if (userContactsResponse is ResponseState.Success<*>) {
                val userContacts = (userContactsResponse.data as List<*>).map { it as Contact }
                val intersect = userContacts.map { it.id }.intersect(allUsers.map { it.id }.toSet())
                val mappedAllUsers = allUsers.map {
                    it.toAddContactsContact()
                }.map {
                    it.copy(isAdded = (intersect.contains(it.id)))
                }
                updateState { copy(allContacts = mappedAllUsers) }
            }
        }
    }

    fun updateState(reducer: AddContactsState.() -> AddContactsState) {
        _addContactsState.update(reducer)
    }

    fun updateContactsStateOnUi() {
        viewModelScope.launch {
            updateAddedContactsAppearance()
        }
    }

    fun addToContactList(contactId: Int) {
        viewModelScope.launch {
            updateProgress(contactId)
            contactRepository.addContact(contactId)
            _addedContactState.emit(contactRepository.getUserById(contactId).toAddContactsContact())
            updateAddedContactsAppearance()
            updateProgress(contactId, false)
        }
    }
}