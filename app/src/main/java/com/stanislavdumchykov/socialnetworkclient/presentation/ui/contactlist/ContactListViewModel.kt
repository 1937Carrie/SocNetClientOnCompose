package com.stanislavdumchykov.socialnetworkclient.presentation.ui.contactlist

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stanislavdumchykov.socialnetworkclient.data.UserList
import com.stanislavdumchykov.socialnetworkclient.domain.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ContactListViewModel @Inject constructor() : ViewModel() {
    private var _userList = mutableStateListOf<User>()
    val userList: SnapshotStateList<User> = _userList

    init {
        viewModelScope.launch {
            _userList.addAll(UserList().users)
        }
    }

    fun addUser(index: Int, user: User) {
        _userList.add(index, user)
    }

    fun removeUser(user: User) {
        _userList.remove(user)
    }
}