package com.dumchykov.socialnetworkdemo.ui.screens.login

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class LogInViewModel @Inject constructor() : ViewModel() {
    private val _logInState = MutableStateFlow(LogInState())
    val logInState get() = _logInState.asStateFlow()

    fun updateState(reducer: LogInState.() -> LogInState) {
        _logInState.update(reducer)
    }
}