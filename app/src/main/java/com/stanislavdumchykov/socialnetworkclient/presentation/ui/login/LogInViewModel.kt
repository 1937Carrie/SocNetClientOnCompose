package com.stanislavdumchykov.socialnetworkclient.presentation.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stanislavdumchykov.socialnetworkclient.R
import com.stanislavdumchykov.socialnetworkclient.domain.model.User
import com.stanislavdumchykov.socialnetworkclient.domain.model.requestModels.AuthorizeModel
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
class LogInViewModel @Inject constructor(
    private val storage: StorageRepository,
    private val serverRepository: NetworkUsersRepository
) : ViewModel() {

    private val _status = MutableLiveData<Response<Status>>()
    val status: LiveData<Response<Status>> = _status

    private val _user = MutableLiveData<User>()
    val user: LiveData<User> = _user

    fun authorizeUser(email: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            setLoadingStatus(_status)
            val response = try {
                serverRepository.authorizeUser(AuthorizeModel(email, password))
            } catch (e: IOException) {
                setErrorStatus(_status, R.string.messageIOException)
                return@launch
            } catch (e: HttpException) {
                setErrorStatus(_status, R.string.messageHTTPException)
                return@launch
            }

            if (response.isSuccessful && response.body() != null) {
//                val userDao = db.userDao()
//                if (userDao.getUser() != null) userDao.delete(userDao.getUser())

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
//                userDao.insert(userData)

                storage.saveString(
                    Constants.ACCESS_TOKEN, response.body()?.data?.accessToken ?: ""
                )
                setSuccessStatus(_status)
            } else {
                setErrorStatus(_status, R.string.messageUnexpectedState)
            }
        }
    }

    private fun setLoadingStatus(destination: MutableLiveData<Response<Status>>) {
        destination.postValue(Response.loading(null))
    }

    private fun setErrorStatus(
        destination: MutableLiveData<Response<Status>>, messageResourceId: Int
    ) {
        destination.postValue(Response.error(messageResourceId, null))
    }

    private fun setSuccessStatus(destination: MutableLiveData<Response<Status>>) {
        destination.postValue(Response.success(Status.SUCCESS))
    }
}