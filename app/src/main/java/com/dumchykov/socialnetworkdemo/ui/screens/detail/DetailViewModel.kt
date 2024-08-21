package com.dumchykov.socialnetworkdemo.ui.screens.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.dumchykov.contactsprovider.domain.BaseContact
import com.dumchykov.contactsprovider.domain.Contact
import com.dumchykov.socialnetworkdemo.navigation.parcelableType
import com.dumchykov.socialnetworkdemo.ui.screens.Detail
import com.dumchykov.socialnetworkdemo.webapi.domain.ContactRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.reflect.typeOf

@HiltViewModel
class DetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    val contactRepository: ContactRepository,
) : ViewModel() {
    private val _detailState = MutableStateFlow(DetailState())
    val detailState get() = _detailState.asStateFlow()

    init {
        viewModelScope.launch {
            val typeMap = mapOf(typeOf<Contact>() to parcelableType<Contact>())
            val contact = savedStateHandle.toRoute<Detail>(typeMap).contact
            val inFriendList = contactInFriendList(contact)
            updateState {
                copy(contact = contact, inFriendList = inFriendList)
            }
        }
    }

    private suspend fun contactInFriendList(contact: BaseContact): Boolean {
        val userContacts = contactRepository.getUserContacts()
        val result = userContacts.count { it.id == contact.id } != 0
        return result
    }

    fun updateState(reducer: DetailState.() -> DetailState) {
        _detailState.update(reducer)
    }

    fun addToContacts() {
        viewModelScope.launch {
            val contact = detailState.value.contact
            contactRepository.addContact(contact.id)
            val inFriendList = contactInFriendList(contact)
            updateState { copy(inFriendList = inFriendList) }
        }
    }
}
