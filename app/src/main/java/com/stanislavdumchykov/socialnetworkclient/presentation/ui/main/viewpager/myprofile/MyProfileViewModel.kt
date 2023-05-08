package com.stanislavdumchykov.socialnetworkclient.presentation.ui.main.viewpager.myprofile

import androidx.lifecycle.ViewModel
import com.stanislavdumchykov.socialnetworkclient.data.database.user.User
import com.stanislavdumchykov.socialnetworkclient.domain.repository.DatabaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MyProfileViewModel @Inject constructor(
    private val databaseRepository: DatabaseRepository,
) : ViewModel() {
    private var user = User()

    fun getUser(): User {
        runBlocking {
            withContext(Dispatchers.IO) {
                user = databaseRepository.getDatabase().userDao().getUser()
            }
        }

        return user
    }
}