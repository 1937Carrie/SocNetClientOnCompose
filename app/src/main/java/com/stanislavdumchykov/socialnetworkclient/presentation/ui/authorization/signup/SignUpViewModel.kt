package com.stanislavdumchykov.socialnetworkclient.presentation.ui.authorization.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stanislavdumchykov.socialnetworkclient.R
import com.stanislavdumchykov.socialnetworkclient.domain.model.User
import com.stanislavdumchykov.socialnetworkclient.domain.model.requestModels.EditProfileUser
import com.stanislavdumchykov.socialnetworkclient.domain.repository.NetworkUsersRepository
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
class SignUpViewModel @Inject constructor(
    private val storage: StorageRepository,
    private val serverRepository: NetworkUsersRepository,
) : ViewModel() {
    private val _statusNetwork = MutableLiveData<Response<Status>>()
    val statusNetwork: LiveData<Response<Status>> = _statusNetwork

    private val _statusUser = MutableLiveData<Response<Status>>()
    val statusUser: LiveData<Response<Status>> = _statusUser

    private val _user = MutableLiveData<User>()
    val user: LiveData<User> = _user

    private val _accessToken = MutableLiveData<String>()

    init {
        getAccessToken()
    }

    fun register(email: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            setLoadingStatus(_statusNetwork)
            val response = try {
                serverRepository.registerUser(email, password)
            } catch (e: IOException) {
                setErrorStatus(_statusNetwork, R.string.messageIOException)
                return@launch
            } catch (e: HttpException) {
                setErrorStatus(_statusNetwork, R.string.messageHTTPException)
                return@launch
            }

            if (response.isSuccessful && response.body() != null) {
                val user = response.body()?.data?.user

                val userData = User(
                    address = user?.address,
                    birthday = user?.birthday,
                    career = user?.career,
                    email = user?.email,
                    facebook = user?.facebook,
                    id = user?.id ?: 0,
                    image = user?.image,
                    instagram = user?.instagram,
                    linkedin = user?.linkedin,
                    name = user?.name,
                    phone = user?.phone,
                    twitter = user?.twitter
                )

                _user.postValue(userData)

                storage.saveString(
                    Constants.ACCESS_TOKEN, response.body()?.data?.accessToken ?: ""
                )
                setSuccessStatus(_statusNetwork)
            } else {
                setErrorStatus(_statusNetwork, R.string.messageUnexpectedState)
            }
        }
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
                    _user.value?.id ?: 0,
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
                    id = user?.id ?: 0,
                    image = user?.image,
                    instagram = user?.instagram,
                    linkedin = user?.linkedin,
                    name = user?.name,
                    phone = user?.phone,
                    twitter = user?.twitter
                )
                _user.postValue(userData)

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

    fun clearAllStatuses() {
        _statusNetwork.value = null
        _statusUser.value = null
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