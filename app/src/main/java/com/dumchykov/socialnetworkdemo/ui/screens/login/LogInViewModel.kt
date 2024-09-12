package com.dumchykov.socialnetworkdemo.ui.screens.login

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dumchykov.datastore.data.DataStoreProvider
import com.dumchykov.socialnetworkdemo.webapi.domain.ContactRepository
import com.dumchykov.socialnetworkdemo.webapi.domain.ResponseState
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
    private val contactRepository: ContactRepository,
) : ViewModel() {
    private val _logInState = MutableStateFlow(LogInState())
    val logInState get() = _logInState.asStateFlow()

    init {
        readCredentials()
    }

    private fun readCredentials() {
        viewModelScope.launch {
            val email = dataStoreProvider.readEmail().first()
            val password = dataStoreProvider.readPassword().first()

            if (email.isEmpty() || password.isEmpty()) return@launch
            updateState {
                copy(
                    email = email,
                    password = password,
                    autoLogin = true
                )
            }
        }
    }

    private fun updateProgress(startProgress: Boolean = true) {
        updateState { copy(isUiStateUpdating = startProgress) }
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

    fun authorize() {
        viewModelScope.launch {
            updateProgress()
            val email = logInState.value.email
            val password = logInState.value.password
            updateState { copy(responseState = ResponseState.Loading) }
            when (val authStatus = contactRepository.authorize(email, password)) {
                is ResponseState.Error -> {
                    updateState { copy(responseState = authStatus) }
                }

                is ResponseState.HttpCode -> {
                    updateState { copy(responseState = authStatus) }
                }

                is ResponseState.Success<*> -> {
                    updateState { copy(responseState = authStatus) }
                }

                else -> {}
            }

            updateProgress(false)
        }
    }
}