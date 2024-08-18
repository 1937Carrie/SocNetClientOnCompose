package com.dumchykov.socialnetworkdemo.ui.screens.login

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dumchykov.datastore.data.DataStoreProvider
import com.dumchykov.socialnetworkdemo.webapi.data.ContactWebProvider
import com.dumchykov.socialnetworkdemo.webapi.domain.ContactRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LogInViewModel @Inject constructor(
    private val dataStoreProvider: DataStoreProvider,
    private val contactWebProvider: ContactRepository,
) : ViewModel() {
    private val _logInState = MutableStateFlow(LogInState())
    val logInState get() = _logInState.asStateFlow()

    init {
        readCredentials()
    }

    fun updateState(reducer: LogInState.() -> LogInState) {
        _logInState.update(reducer)
    }

    fun validateEmail(email: String) {
        val result = email.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()
        updateState { copy(emailError = result.not()) }
    }

    fun validatePassword(password: String) {
        val result = password.isNotEmpty()
        updateState { copy(passwordError = result.not()) }
    }

    fun saveCredentials() {
        viewModelScope.launch {
            dataStoreProvider.saveCredentials(logInState.value.email, logInState.value.password)
        }
    }

    private fun readCredentials() {
        viewModelScope.launch {
            val email = dataStoreProvider.readEmail().first()
            val password = dataStoreProvider.readPassword().first()
            if (email.isNotEmpty() && password.isNotEmpty()) {
                updateState {
                    copy(
                        email = email,
                        password = password,
                        autoLogin = true
                    )
                }
            }
        }
    }

    fun authorize() {
        viewModelScope.launch {
            val email = logInState.value.email
            val password = logInState.value.password
            val response = contactWebProvider.authorize(email, password)
            if (response.code == 200) {
                updateState { copy(name = response.data.user.name ?: "null field") }
            }
        }
    }
}