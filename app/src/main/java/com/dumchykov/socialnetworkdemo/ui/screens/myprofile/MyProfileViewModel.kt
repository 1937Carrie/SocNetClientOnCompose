package com.dumchykov.socialnetworkdemo.ui.screens.myprofile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dumchykov.datastore.data.DataStoreProvider
import com.dumchykov.socialnetworkdemo.ui.screens.myprofile.data.toMyProfileContact
import com.dumchykov.socialnetworkdemo.webapi.domain.ContactRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyProfileViewModel @Inject constructor(
    private val contactRepository: ContactRepository,
    private val dataStoreProvider: DataStoreProvider,
) : ViewModel() {
    private val _myProfileState = MutableStateFlow(MyProfileState())
    val myProfileState get() = _myProfileState.asStateFlow()

    init {
        readAuthorizedUser()
    }

    private fun readAuthorizedUser() {
        viewModelScope.launch {
            val user = contactRepository.getUserById(-1).toMyProfileContact()
            updateState { copy(user = user) }
        }
    }

    private fun updateState(reducer: MyProfileState.() -> MyProfileState) {
        _myProfileState.update(reducer)
    }

    fun clearCredentials() {
        viewModelScope.launch {
            dataStoreProvider.clearCredentials()
            _myProfileState.update { _myProfileState.value.copy(credentialsIsCleared = true) }
        }
    }
}
