package com.dumchykov.socialnetworkdemo.ui.screens.signup

import android.util.Patterns
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class SignUpViewModel : ViewModel() {
    private val _signUpState = MutableStateFlow(SignUpState())
    val signUpState get() = _signUpState.asStateFlow()

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
}