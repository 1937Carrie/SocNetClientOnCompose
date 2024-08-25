package com.dumchykov.socialnetworkdemo.ui.screens.signupextended

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dumchykov.datastore.data.DataStoreProvider
import com.dumchykov.socialnetworkdemo.webapi.domain.ContactRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpExtendedViewModel @Inject constructor(
    val contactRepository: ContactRepository,
    val dataStoreProvider: DataStoreProvider,
) : ViewModel() {
    private val _signUpExtendedState = MutableStateFlow(SignUpExtendedState())
    val signUpExtendedState get() = _signUpExtendedState.asStateFlow()

    fun updateState(reducer: SignUpExtendedState.() -> SignUpExtendedState) {
        _signUpExtendedState.update(reducer)
    }

    fun editUser() {
        viewModelScope.launch {
            val contact = contactRepository.getUser()
            val editedContact = contact.copy(
                name = signUpExtendedState.value.userName,
                phone = signUpExtendedState.value.mobilePhone
            )
            contactRepository.editUser(editedContact)
            updateState { copy(navigateForward = true) }
        }
    }
}