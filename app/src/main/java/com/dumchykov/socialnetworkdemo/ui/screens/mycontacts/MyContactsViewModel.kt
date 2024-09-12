package com.dumchykov.socialnetworkdemo.ui.screens.mycontacts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dumchykov.data.Contact
import com.dumchykov.database.ContactsDatabase
import com.dumchykov.socialnetworkdemo.ui.screens.mycontacts.data.MyContactsIndicatorContact
import com.dumchykov.socialnetworkdemo.ui.screens.mycontacts.data.toMyContactsIndicatorContact
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
class MyContactsViewModel @Inject constructor(
    private val contactRepository: ContactRepository,
    private val database: ContactsDatabase,
) : ViewModel() {
    private val _myContactsState = MutableStateFlow(MyContactsState())
    val myContactsState get() = _myContactsState.asStateFlow()

    private val _deletedContactState = MutableSharedFlow<MyContactsIndicatorContact>()
    val deleteContactState get() = _deletedContactState.asSharedFlow()

    private fun updateMultiselectState(): Boolean {
        return myContactsState.value.contacts.count { it.isChecked } != 0
    }

    private suspend fun getAllUsers() {
        updateState { copy(responseState = ResponseState.Loading) }
        val getUsersResponse = contactRepository.getUsers()
        if (getUsersResponse is ResponseState.Success<*>) {
            updateState { copy(isDatabaseGottenOnStart = true) }
        }
        updateState { copy(responseState = getUsersResponse) }
    }

    private suspend fun updateContactsAppearance() {
        val userContactsResponse = contactRepository.getUserContacts()
        if (userContactsResponse is ResponseState.Success<*>) {
            val userContacts =
                (userContactsResponse.data as List<*>).map { (it as Contact).toMyContactsIndicatorContact() }
            updateState { copy(contacts = userContacts) }
        }
        updateState { copy(responseState = userContactsResponse) }
    }

    fun updateState(reducer: MyContactsState.() -> MyContactsState) {
        _myContactsState.update(reducer)
    }

    fun addContact(contact: MyContactsIndicatorContact) {
        viewModelScope.launch {
            val onAddContactResponse = contactRepository.addContact(contact.id)
            if (onAddContactResponse is ResponseState.Success<*>) {
                val userContactsResponse = contactRepository.getUserContacts()
                if (userContactsResponse is ResponseState.Success<*>) {
                    val userContacts = (userContactsResponse.data as List<*>)
                        .map { (it as Contact).toMyContactsIndicatorContact() }
                    updateState { copy(contacts = userContacts) }
                }
            }
        }
    }

    fun deleteContact(contactId: Int) {
        viewModelScope.launch {
            updateProgress(contactId)
            val onDeleteResponse = contactRepository.deleteContact(contactId)
            if (onDeleteResponse is ResponseState.Success<*>) {
                val contact = getContactById(contactId)
                updateContactsAppearance()
                _deletedContactState.emit(contact)
            } else {
                updateProgress(contactId, false)
            }
        }
    }

    private fun deleteMultipleContacts(idList: List<Int>) {
        viewModelScope.launch {
            updateState { copy(responseState = ResponseState.Loading) }
            idList.forEach { id -> contactRepository.deleteContact(id) }
            updateContactsAppearance()
            updateState {
                copy(
                    responseState = ResponseState.Success<Nothing>(),
                    isMultiselect = updateMultiselectState()
                )
            }
        }
    }

    private fun updateProgress(contactId: Int, startProgress: Boolean = true) {
        val contacts = myContactsState.value.contacts.toMutableList()
        val index = contacts.indexOf(contacts.first { it.id == contactId })
        contacts[index] = contacts[index].copy(updateUiState = startProgress)
        updateState { copy(contacts = contacts) }
    }

    private fun getContactById(contactId: Int): MyContactsIndicatorContact {
        val contacts = myContactsState.value.contacts.toMutableList()
        val index = contacts.indexOf(contacts.first { it.id == contactId })
        return contacts[index]
    }

    fun changeContactSelectedState(contact: MyContactsIndicatorContact) {
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
        deleteMultipleContacts(contacts.filter { it.isChecked }.map { it.id })
    }

    fun updateListOnEnter() {
        viewModelScope.launch {
            val needToUpdateAllContacts = myContactsState.value.isDatabaseGottenOnStart.not()
            if (needToUpdateAllContacts) {
                getAllUsers()
            }
            updateContactsAppearance()
        }
    }
}