package com.stanislavdumchykov.socialnetworkclient.presentation.ui.main.alluserslist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stanislavdumchykov.socialnetworkclient.R
import com.stanislavdumchykov.socialnetworkclient.domain.model.User
import com.stanislavdumchykov.socialnetworkclient.domain.repository.NetworkUsersRepository
import com.stanislavdumchykov.socialnetworkclient.domain.repository.StorageRepository
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
class AllUsersListViewModel @Inject constructor(
    private val storage: StorageRepository,
    private val serverRepository: NetworkUsersRepository,
) : ViewModel() {
    private val _statusNetwork = MutableLiveData<Response<Status>>()
    val statusNetwork: LiveData<Response<Status>> = _statusNetwork

    private val _allUsers = MutableStateFlow<List<User>>(emptyList())
    val allUsers: Flow<List<User>> = _allUsers

    private val _accessToken = MutableLiveData<String>()

    init {
        getAccessToken()
    }

    fun getAllUsers() {
        viewModelScope.launch(Dispatchers.IO) {
            setLoadingStatus(_statusNetwork)

            val response = try {
                serverRepository.getUsers(Constants.BEARER_TOKEN + _accessToken.value)
            } catch (e: IOException) {
                setErrorStatus(_statusNetwork, R.string.messageIOException)
                return@launch
            } catch (e: HttpException) {
                setErrorStatus(_statusNetwork, R.string.messageHTTPException)
                return@launch
            }

            if (response.isSuccessful && response.body() != null) {
                response.body()?.let {
                    _allUsers.value = it.data.users
                }
                setSuccessStatus(_statusNetwork)
            } else {
                setErrorStatus(_statusNetwork, R.string.messageUnexpectedState)
            }
        }
    }

    private fun setSuccessStatus(status: MutableLiveData<Response<Status>>) {
        status.postValue(Response.success(Status.SUCCESS))
    }

    private fun setLoadingStatus(status: MutableLiveData<Response<Status>>) {
        status.postValue(Response.loading(null))
    }

    private fun setErrorStatus(
        status: MutableLiveData<Response<Status>>, messageResourceId: Int
    ) {
        status.postValue(Response.error(messageResourceId, null))
    }

    private fun getAccessToken() {
        viewModelScope.launch(Dispatchers.IO) {
            storage.getToken.collect {
                _accessToken.postValue(it)
            }
        }
    }
}