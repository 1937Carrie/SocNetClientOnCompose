package com.stanislavdumchykov.socialnetworkclient.presentation.ui.viewpager.contactlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stanislavdumchykov.socialnetworkclient.data.UserList
import com.stanislavdumchykov.socialnetworkclient.domain.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ContactListViewModel @Inject constructor() : ViewModel() {
    private var _userList = MutableStateFlow<List<User>>(emptyList())
    val userList: Flow<List<User>> = _userList

    init {
        viewModelScope.launch {
            _userList.value = UserList().users
        }
    }

    fun addUser(index: Int, user: User) {
        _userList.value = _userList.value.toMutableList().apply { add(index, user) }
    }

    fun removeUser(user: User) {
        _userList.value = _userList.value - (user)
    }
}