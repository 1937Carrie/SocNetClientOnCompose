package com.dumchykov.socialnetworkdemo.ui.screens.myprofile

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class MyProfileViewModel : ViewModel() {
    private val _myProfileState = MutableStateFlow(MyProfileState())
    val myProfileState get() = _myProfileState.asStateFlow()

    fun parseEmail(email: String) {
        val parsedName = email
            .substringBefore('@')
            .split('.')
            .fold("") { acc, s -> "$acc ${s.replaceFirstChar { it.uppercase() }} " }
            .trim()
        _myProfileState.update { _myProfileState.value.copy(name = parsedName) }
    }
}