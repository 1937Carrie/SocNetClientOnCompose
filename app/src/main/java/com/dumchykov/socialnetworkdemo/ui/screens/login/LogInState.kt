package com.dumchykov.socialnetworkdemo.ui.screens.login

data class LogInState(
    val email: String = "uzumymw@email.com",
    val emailError: Boolean = false,
    val emailIsFocused: Boolean = false,
    val password: String = "kjkszpj",
    val passwordError: Boolean = false,
    val passwordIsFocused: Boolean = false,
    val rememberMe: Boolean = true,
    val autoLogin: Boolean = false,
    val navigateToMyProfile: Boolean = false,
    val updateUiState: Boolean = false,
)
