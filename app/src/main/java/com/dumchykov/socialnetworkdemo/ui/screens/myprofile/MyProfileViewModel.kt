package com.dumchykov.socialnetworkdemo.ui.screens.myprofile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dumchykov.contactsprovider.data.toContact
import com.dumchykov.datastore.data.DataStoreProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyProfileViewModel @Inject constructor(
    private val dataStoreProvider: DataStoreProvider,
) : ViewModel() {
    private val _myProfileState = MutableStateFlow(MyProfileState())
    val myProfileState get() = _myProfileState.asStateFlow()

    init {
        viewModelScope.launch {
            val user = dataStoreProvider.getContact().first().toContact()
            update { copy(user = user) }
        }
    }

    fun update(reducer: MyProfileState.() -> MyProfileState) {
        _myProfileState.update(reducer)
    }

    private fun parseEmail(email: String): String {
        return email
            .substringBefore('@')
            .split('.')
            .fold("") { acc, s -> "$acc ${s.replaceFirstChar { it.uppercase() }} " }
            .trim()

    }

    fun clearCredentials() {
        viewModelScope.launch {
            dataStoreProvider.clearCredentials()
            _myProfileState.update { _myProfileState.value.copy(credentialsIsCleared = true) }
        }
    }
}
