package com.dumchykov.socialnetworkdemo.ui.screens.login

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dumchykov.datastore.data.DataStoreProvider
import com.dumchykov.socialnetworkdemo.webapi.domain.ContactRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
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
            val email: Flow<String> = dataStoreProvider.readEmail()
            val password: Flow<String> = dataStoreProvider.readPassword()

            email
                .combine(password) { email, password -> email to password }
                .distinctUntilChanged()
                .collect { (email, password) ->
                    if (email.isEmpty() || password.isEmpty()) return@collect
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

    private fun updateProgress(startProgress: Boolean = true) {
        updateState { copy(updateUiState = startProgress.not()) }
    }

    fun authorize() {
        viewModelScope.launch {
            updateProgress()
            val email = logInState.value.email
            val password = logInState.value.password
            val authStatus = async { contactRepository.authorize(email, password) }.await()

            if (authStatus) {
                updateState { copy(navigateToMyProfile = true) }
            }
            updateProgress(false)
        }
    }
}