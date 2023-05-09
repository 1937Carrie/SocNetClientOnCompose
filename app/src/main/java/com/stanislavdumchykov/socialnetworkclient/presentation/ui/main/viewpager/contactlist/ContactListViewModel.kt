package com.stanislavdumchykov.socialnetworkclient.presentation.ui.main.viewpager.contactlist

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stanislavdumchykov.socialnetworkclient.R
import com.stanislavdumchykov.socialnetworkclient.domain.model.LocalUser
import com.stanislavdumchykov.socialnetworkclient.domain.repository.DatabaseRepository
import com.stanislavdumchykov.socialnetworkclient.domain.repository.NetworkUsersRepository
import com.stanislavdumchykov.socialnetworkclient.domain.repository.StorageRepository
import com.stanislavdumchykov.socialnetworkclient.domain.repository.UsersRepository
import com.stanislavdumchykov.socialnetworkclient.presentation.utils.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import com.stanislavdumchykov.socialnetworkclient.data.database.user.User as UserDB
import com.stanislavdumchykov.socialnetworkclient.domain.model.User as UserNetwork

@HiltViewModel
class ContactListViewModel @Inject constructor(
    private val storage: StorageRepository,
    private val usersRepository: UsersRepository,
    private val serverApi: NetworkUsersRepository,
    private val databaseRepository: DatabaseRepository,
) : ViewModel() {
    private val _localUserList = MutableStateFlow<List<LocalUser>>(emptyList())
    val localUserList: StateFlow<List<LocalUser>> = _localUserList.asStateFlow()

    private val _userContacts = MutableStateFlow<List<UserNetwork>>(emptyList())
    val userContacts: StateFlow<List<UserNetwork>> = _userContacts.asStateFlow()

    private val _user = MutableStateFlow(UserDB())
    val user: StateFlow<UserDB> = _user.asStateFlow()

    private val _accessToken = MutableStateFlow("")

    init {
        viewModelScope.launch(Dispatchers.IO) {
//            _localUserList.value = usersRepository.getUsers()
            getAccessToken()
            setUser()
        }
    }

    fun addUser(index: Int, localUser: LocalUser) {
        _localUserList.value = _localUserList.value.toMutableList().apply { add(index, localUser) }
    }

    fun addUser2(index: Int, user: UserNetwork) {
        _userContacts.value = _userContacts.value.toMutableList().apply { add(index, user) }
    }

    fun removeUser(index: Int) {
//        _localUserList.value = _localUserList.value.toMutableList().apply { removeAt(index) }
        _userContacts.value = _userContacts.value.toMutableList().apply { removeAt(index) }
    }

    fun apiGetUserContacts() {
        viewModelScope.launch(Dispatchers.IO) {

            val response = try {
                serverApi.getAccountUsers(
                    databaseRepository.getDatabase().userDao().getUser().serverId,
                    Constants.BEARER_TOKEN + _accessToken.value
                )
            } catch (e: IOException) {
                Log.e("IOException", R.string.messageIOException.toString())
                return@launch
            } catch (e: HttpException) {
                Log.e("HttpException", R.string.messageHTTPException.toString())
                return@launch
            }

            if (response.isSuccessful && response.body() != null) {
                response.body()?.let {
                    _userContacts.value = it.data.contacts
                }
            } else {
                Log.e("UnexpectedException", R.string.messageUnexpectedState.toString())
            }
        }
    }

    private fun setUser() {
        viewModelScope.launch(Dispatchers.IO) {
            _user.value = databaseRepository.getDatabase().userDao().getUser()
        }
    }

    private fun getAccessToken() {
        viewModelScope.launch(Dispatchers.IO) {
            storage.getToken.collect {
                _accessToken.value = it
            }
        }
    }
}