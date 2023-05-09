package com.stanislavdumchykov.socialnetworkclient.presentation.ui.authorization.signup

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stanislavdumchykov.socialnetworkclient.R
import com.stanislavdumchykov.socialnetworkclient.domain.model.User
import com.stanislavdumchykov.socialnetworkclient.domain.model.requestModels.EditProfileUser
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
class SignUpViewModel @Inject constructor(
    private val storage: StorageRepository,
    private val serverRepository: NetworkUsersRepository,
) : ViewModel() {

    private val _user = MutableStateFlow(User())
    val user: StateFlow<User> = _user.asStateFlow()

    private val _accessToken = MutableStateFlow("")

    init {
        getAccessToken()
    }

    fun register(email: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = try {
                serverRepository.registerUser(email, password)
            } catch (e: IOException) {
                Log.e("IOException", R.string.messageIOException.toString())
                return@launch
            } catch (e: HttpException) {
                Log.e("HttpException", R.string.messageHTTPException.toString())
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

                _user.value = userData

                storage.saveString(
                    Constants.ACCESS_TOKEN, response.body()?.data?.accessToken ?: ""
                )
            } else {
                Log.e("UnexpectedException", R.string.messageUnexpectedState.toString())
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

            val response = try {
                serverRepository.editUser(
                    _user.value.id,
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
                Log.e("IOException", R.string.messageIOException.toString())
                return@launch
            } catch (e: HttpException) {
                Log.e("HttpException", R.string.messageHTTPException.toString())
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

                _user.value = userData

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