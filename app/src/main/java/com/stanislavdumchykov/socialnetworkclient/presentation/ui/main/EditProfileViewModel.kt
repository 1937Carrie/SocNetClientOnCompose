package com.stanislavdumchykov.socialnetworkclient.presentation.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stanislavdumchykov.socialnetworkclient.R
import com.stanislavdumchykov.socialnetworkclient.data.database.user.User
import com.stanislavdumchykov.socialnetworkclient.domain.api.ServerApi
import com.stanislavdumchykov.socialnetworkclient.domain.model.requestModels.EditProfileUser
import com.stanislavdumchykov.socialnetworkclient.domain.repository.DatabaseRepository
import com.stanislavdumchykov.socialnetworkclient.domain.repository.StorageRepository
import com.stanislavdumchykov.socialnetworkclient.presentation.utils.Constants
import com.stanislavdumchykov.socialnetworkclient.presentation.utils.Response
import com.stanislavdumchykov.socialnetworkclient.presentation.utils.Status
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class EditProfileViewModel @Inject constructor(
    private val storage: StorageRepository,
    private val serverRepository: ServerApi,
    private val databaseRepository: DatabaseRepository,
) : ViewModel() {
    private var user1 = User()

    private val _statusNetwork = MutableLiveData<Response<Status>>()
    val statusNetwork: LiveData<Response<Status>> = _statusNetwork

    private val _accessToken = MutableLiveData<String>()

    init {
        getAccessToken()
    }

    fun getUser(): User {
        viewModelScope.launch(Dispatchers.IO) {
            user1 = databaseRepository.getDatabase().userDao().getUser()
        }

        return user1
    }

    fun editProfile(
        name: String = "",
        career: String = "",
        phone: String = "",
        address: String = "",
        birthday: String = ""
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            setLoadingStatus(_statusNetwork)

            val response = try {
                serverRepository.editUser(
                    user1.serverId,
                    Constants.BEARER_TOKEN + _accessToken.value,
                    EditProfileUser(
                        name,
                        phone,
                        address,
                        career,
                        birthday,
                    )
                )
            } catch (e: IOException) {
                setErrorStatus(_statusNetwork, R.string.messageIOException)
                return@launch
            } catch (e: HttpException) {
                setErrorStatus(_statusNetwork, R.string.messageHTTPException)
                return@launch
            }
            if (response.isSuccessful) {
                val user = response.body()?.data?.user
                val userData = User(
                    address = user?.address,
                    birthday = user?.birthday,
                    career = user?.career,
                    email = user?.email,
                    facebook = user?.facebook,
                    serverId = user?.id ?: 0,
                    image = user?.image,
                    instagram = user?.instagram,
                    linkedin = user?.linkedin,
                    name = user?.name,
                    phone = user?.phone,
                    twitter = user?.twitter,
                    created_at = user?.created_at,
                    updated_at = user?.updated_at
                )
                databaseRepository.getDatabase().userDao().createUser(userData)

                setSuccessStatus(_statusNetwork)
            } else {
                setErrorStatus(_statusNetwork, R.string.messageUnexpectedState)
            }
        }
    }

    fun clearAllStatuses() {
        _statusNetwork.value = null
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