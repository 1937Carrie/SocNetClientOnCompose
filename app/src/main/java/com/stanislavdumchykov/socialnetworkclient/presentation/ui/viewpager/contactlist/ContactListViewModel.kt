package com.stanislavdumchykov.socialnetworkclient.presentation.ui.viewpager.contactlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stanislavdumchykov.socialnetworkclient.domain.model.User
import com.stanislavdumchykov.socialnetworkclient.domain.repository.UsersRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ContactListViewModel @Inject constructor(
    private val usersRepository: UsersRepository
) : ViewModel() {
    private val _userList = MutableStateFlow<List<User>>(emptyList())
    val userList: Flow<List<User>> = _userList

    init {
        viewModelScope.launch(Dispatchers.IO) {
            _userList.value = usersRepository.getHardcodedUsers()
        }
    }

    fun addUser(index: Int, user: User) {
        _userList.value = _userList.value.toMutableList().apply { add(index, user) }
    }

    fun removeUser(user: User) {
        _userList.value = _userList.value - (user)
    }
}