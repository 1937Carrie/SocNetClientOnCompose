package com.stanislavdumchykov.socialnetworkclient.presentation.ui.main.editprofile

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stanislavdumchykov.socialnetworkclient.R
import com.stanislavdumchykov.socialnetworkclient.data.database.user.User
import com.stanislavdumchykov.socialnetworkclient.domain.api.ServerApi
import com.stanislavdumchykov.socialnetworkclient.domain.model.requestModels.EditProfileUser
import com.stanislavdumchykov.socialnetworkclient.domain.repository.DatabaseRepository
import com.stanislavdumchykov.socialnetworkclient.domain.repository.StorageRepository
import com.stanislavdumchykov.socialnetworkclient.presentation.utils.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class EditProfileViewModel @Inject constructor(
    private val storage: StorageRepository,
    private val serverRepository: ServerApi,
    private val databaseRepository: DatabaseRepository,
) : ViewModel() {
    private var user = User()

    private val _accessToken = MutableStateFlow("")

    init {
        getAccessToken()
    }

    fun getUser(): User {
        runBlocking {
            withContext(Dispatchers.IO) {
                user = databaseRepository.getDatabase().userDao().getUser()
            }
        }
        return user
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
                    user.serverId,
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