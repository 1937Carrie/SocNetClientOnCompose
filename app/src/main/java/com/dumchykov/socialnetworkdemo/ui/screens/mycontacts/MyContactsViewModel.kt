package com.dumchykov.socialnetworkdemo.ui.screens.mycontacts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.dumchykov.contactsprovider.data.ContactsProvider
import com.dumchykov.contactsprovider.domain.Contact
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class MyContactsViewModel(
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

    fun deleteContact(contact: Contact) {
        val tempContacts = myContactsState.value.contacts.toMutableList()
        tempContacts -= contact
        updateState { copy(contacts = tempContacts) }
    }

    companion object {
        fun factory(): ViewModelProvider.Factory {
            return viewModelFactory {
                initializer {
                    val contactsProvider = ContactsProvider()
                    MyContactsViewModel(contactsProvider)
                }
            }
        }
    }
}