package com.stanislavdumchykov.socialnetworkclient.presentation.ui.main.alluserslist

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stanislavdumchykov.socialnetworkclient.R
import com.stanislavdumchykov.socialnetworkclient.domain.model.User
import com.stanislavdumchykov.socialnetworkclient.domain.repository.NetworkUsersRepository
import com.stanislavdumchykov.socialnetworkclient.domain.repository.StorageRepository
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

@HiltViewModel
class AllUsersListViewModel @Inject constructor(
    private val storage: StorageRepository,
    private val serverRepository: NetworkUsersRepository,
) : ViewModel() {
    private val _allUsers = MutableStateFlow<List<User>>(emptyList())
    val allUsers: StateFlow<List<User>> = _allUsers.asStateFlow()

    private val _accessToken = MutableStateFlow("")

    init {
        getAccessToken()
    }

    fun getAllUsers() {
        viewModelScope.launch(Dispatchers.IO) {
            val response = try {
                serverRepository.getUsers(Constants.BEARER_TOKEN + _accessToken.value)
            } catch (e: IOException) {
                Log.e("IOException", R.string.messageIOException.toString())
                return@launch
            } catch (e: HttpException) {
                Log.e("HttpException", R.string.messageHTTPException.toString())
                return@launch
            }

            if (response.isSuccessful && response.body() != null) {
                response.body()?.let {
                    _allUsers.value = it.data.users
                }
            } else {
                Log.e("UnexpectedException", R.string.messageUnexpectedState.toString())
            }
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