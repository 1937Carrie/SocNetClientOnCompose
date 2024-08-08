package com.dumchykov.socialnetworkdemo.ui.screens.signup

import android.content.Context
import android.util.Log
import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.dumchykov.datastore.data.DataStoreProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SignUpViewModel(
    private val dataStoreProvider: DataStoreProvider,
) : ViewModel() {
    private val _signUpState = MutableStateFlow(SignUpState())
    val signUpState get() = _signUpState.asStateFlow()

    init {
        readCredentials()
    }

    fun updateState(reducer: SignUpState.() -> SignUpState) {
        _signUpState.update(reducer)
    }

    fun validateEmail(email: String) {
        val result = email.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()
        _signUpState.update { _signUpState.value.copy(emailError = result.not()) }
    }

    fun validatePassword(password: String) {
        val result = password.isNotEmpty()
        _signUpState.update { _signUpState.value.copy(passwordError = result.not()) }
    }

    fun saveCredentials() {
        viewModelScope.launch {
            dataStoreProvider.saveCredentials(signUpState.value.email, signUpState.value.password)
        }
    }

    private fun readCredentials() {
        viewModelScope.launch {
            val email = dataStoreProvider.readEmail().first()
            val password = dataStoreProvider.readPassword().first()
            if (email.isNotEmpty() && password.isNotEmpty()) {
                _signUpState.update {
                    _signUpState.value.copy(
                        email = email,
                        password = password,
                        autoLogin = true
                    )
                }
            }
        }
    }

    override fun onCleared() {
        Log.d("AAA", "onCleared: ${this.javaClass.simpleName}")
        super.onCleared()
    }

    companion object {
        fun factory(context: Context): ViewModelProvider.Factory {
            return viewModelFactory {
                initializer {
                    val dataStoreProvider = DataStoreProvider(context)
                    SignUpViewModel(dataStoreProvider)
                }
            }
        }
    }
}