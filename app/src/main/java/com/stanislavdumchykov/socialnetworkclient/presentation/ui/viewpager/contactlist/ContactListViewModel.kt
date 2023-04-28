package com.stanislavdumchykov.socialnetworkclient.presentation.ui.viewpager.contactlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stanislavdumchykov.socialnetworkclient.domain.model.LocalUser
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
    private val _localUserList = MutableStateFlow<List<LocalUser>>(emptyList())
    val localUserList: Flow<List<LocalUser>> = _localUserList

    init {
        viewModelScope.launch(Dispatchers.IO) {
            _localUserList.value = usersRepository.getUsers()
        }
    }

    fun addUser(index: Int, localUser: LocalUser) {
        _localUserList.value = _localUserList.value.toMutableList().apply { add(index, localUser) }
    }

    fun removeUser(localUser: LocalUser) {
        _localUserList.value = _localUserList.value - (localUser)
    }
}