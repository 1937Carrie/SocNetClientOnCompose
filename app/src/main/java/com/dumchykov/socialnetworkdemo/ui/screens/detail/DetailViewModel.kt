package com.dumchykov.socialnetworkdemo.ui.screens.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.dumchykov.data.Contact
import com.dumchykov.socialnetworkdemo.ui.screens.Detail
import com.dumchykov.socialnetworkdemo.webapi.domain.ContactRepository
import com.dumchykov.socialnetworkdemo.webapi.domain.ResponseState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val contactRepository: ContactRepository,

    ) : ViewModel() {
    private val _detailState = MutableStateFlow(DetailState())
    val detailState get() = _detailState.asStateFlow()

    init {
        viewModelScope.launch {
            val contactId = savedStateHandle.toRoute<Detail>().contactId
            val inFriendList = contactInFriendList(contactId)
            val contact = contactRepository.getUserById(contactId)

            updateState { contact.toDetailState(inFriendList) }
        }
    }

    private suspend fun contactInFriendList(contactId: Int): Boolean {
        val userContactsResponse = contactRepository.getUserContacts()
        return if (userContactsResponse is ResponseState.Success<*>) {
            val result = (userContactsResponse.data as List<*>)
                .map { it as Contact }
                .map { it.id }
                .contains(contactId)
            result
        } else {
            false
        }
    }

    fun updateState(reducer: DetailState.() -> DetailState) {
        _detailState.update(reducer)
    }

    fun addToContacts() {
        viewModelScope.launch {
            val contact = detailState.value
            contactRepository.addContact(contact.id)
            val inFriendList = contactInFriendList(contact.id)
            updateState { copy(inFriendList = inFriendList) }
        }
    }
}