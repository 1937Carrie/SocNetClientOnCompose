package com.stanislavdumchykov.socialnetworkclient.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.stanislavdumchykov.socialnetworkclient.domain.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor() : ViewModel() {
    var user by mutableStateOf(User())
        private set

    fun addUser(newUser: User) {
        user = newUser
    }
}