package com.stanislavdumchykov.socialnetworkclient.presentation.ui.main.viewpager.contactlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stanislavdumchykov.socialnetworkclient.R
import com.stanislavdumchykov.socialnetworkclient.domain.model.LocalUser
import com.stanislavdumchykov.socialnetworkclient.domain.model.User
import com.stanislavdumchykov.socialnetworkclient.domain.repository.NetworkUsersRepository
import com.stanislavdumchykov.socialnetworkclient.domain.repository.StorageRepository
import com.stanislavdumchykov.socialnetworkclient.domain.repository.UsersRepository
import com.stanislavdumchykov.socialnetworkclient.presentation.utils.Constants
import com.stanislavdumchykov.socialnetworkclient.presentation.utils.Response
import com.stanislavdumchykov.socialnetworkclient.presentation.utils.Status
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class ContactListViewModel @Inject constructor(
    private val storage: StorageRepository,
    private val usersRepository: UsersRepository,
    private val serverApi: NetworkUsersRepository
) : ViewModel() {
    private val _localUserList = MutableStateFlow<List<LocalUser>>(emptyList())
    val localUserList: Flow<List<LocalUser>> = _localUserList

    private val _userContacts = MutableStateFlow<List<User>>(emptyList())
    val userContacts: Flow<List<User>> = _userContacts

    private val _statusUserContacts = MutableLiveData<Response<Status>>()
    val statusUserContacts: LiveData<Response<Status>> = _statusUserContacts

    private val _user = MutableLiveData<User>()
    val user: LiveData<User> = _user

    private val _statusUser = MutableLiveData<Response<Status>>()
    val statusUser: LiveData<Response<Status>> = _statusUser

    private val _statusNetwork = MutableLiveData<Response<Status>>()
    val statusNetwork: LiveData<Response<Status>> = _statusNetwork

    private val _accessToken = MutableLiveData<String>()

    init {
        viewModelScope.launch(Dispatchers.IO) {
//            _localUserList.value = usersRepository.getUsers()
            getAccessToken()
        }
    }

    fun addUser(index: Int, localUser: LocalUser) {
        _localUserList.value = _localUserList.value.toMutableList().apply { add(index, localUser) }
    }

    fun addUser2(index: Int, user: User) {
        _userContacts.value = _userContacts.value.toMutableList().apply { add(index, user) }
    }

    fun removeUser(index: Int) {
//        _localUserList.value = _localUserList.value.toMutableList().apply { removeAt(index) }
        _userContacts.value = _userContacts.value.toMutableList().apply { removeAt(index) }
    }

    fun apiGetUserContacts() {
        viewModelScope.launch(Dispatchers.IO) {
            setLoadingStatus(_statusNetwork)

            val response = try {
                serverApi.getAccountUsers(
                    user.value?.id ?: 0,
                    Constants.BEARER_TOKEN + _accessToken.value
                )
            } catch (e: IOException) {
                setErrorStatus(_statusNetwork, R.string.messageIOException)
                return@launch
            } catch (e: HttpException) {
                setErrorStatus(_statusNetwork, R.string.messageHTTPException)
                return@launch
            }

            if (response.isSuccessful && response.body() != null) {
                response.body()?.let {
                    _userContacts.value = it.data.contacts
                }
                setSuccessStatus(_statusNetwork)
            } else {
                setErrorStatus(_statusNetwork, R.string.messageUnexpectedState)
            }
        }
    }

    private fun setLoadingStatus(status: MutableLiveData<Response<Status>>) {
        status.postValue(Response.loading(null))
    }

    private fun setSuccessStatus(status: MutableLiveData<Response<Status>>) {
        status.postValue(Response.success(Status.SUCCESS))
    }

    private fun setErrorStatus(
        status: MutableLiveData<Response<Status>>, messageResourceId: Int
    ) {
        status.postValue(Response.error(messageResourceId, null))
    }

    fun setUser(newUser: User) {
        viewModelScope.launch(Dispatchers.IO) {
            setLoadingStatus(_statusUser)
            _user.postValue(newUser)
            setSuccessStatus(_statusUser)
        }
    }

    private fun getAccessToken() {
        viewModelScope.launch(Dispatchers.IO) {
            storage.getToken.collect {
                _accessToken.postValue(it)
            }
        }
    }
}