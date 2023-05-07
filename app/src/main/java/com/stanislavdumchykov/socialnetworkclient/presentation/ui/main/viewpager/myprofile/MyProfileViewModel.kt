package com.stanislavdumchykov.socialnetworkclient.presentation.ui.main.viewpager.myprofile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stanislavdumchykov.socialnetworkclient.data.database.user.User
import com.stanislavdumchykov.socialnetworkclient.domain.repository.DatabaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyProfileViewModel @Inject constructor(
    private val databaseRepository: DatabaseRepository,
) : ViewModel() {
    private var user1 = User()

    fun getUser(): User {
        viewModelScope.launch(Dispatchers.IO) {
            user1 = databaseRepository.getDatabase().userDao().getUser()
        }

        return user1
    }
}