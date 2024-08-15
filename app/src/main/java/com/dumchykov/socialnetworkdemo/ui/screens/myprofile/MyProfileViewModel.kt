package com.dumchykov.socialnetworkdemo.ui.screens.myprofile

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.toRoute
import com.dumchykov.datastore.data.DataStoreProvider
import com.dumchykov.socialnetworkdemo.ui.screens.MyProfile
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MyProfileViewModel(
    private val dataStoreProvider: DataStoreProvider,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private val _myProfileState = MutableStateFlow(MyProfileState())
    val myProfileState get() = _myProfileState.asStateFlow()

    init {
        val email = savedStateHandle.toRoute<MyProfile>().email
        update { copy(argEmail = email) }
        val parsedName = parseEmail(email)
        update { copy(name = parsedName) }
    }

    fun update(reducer: MyProfileState.() -> MyProfileState) {
        _myProfileState.update(reducer)
    }

    private fun parseEmail(email: String): String {
        return email
            .substringBefore('@')
            .split('.')
            .fold("") { acc, s -> "$acc ${s.replaceFirstChar { it.uppercase() }} " }
            .trim()

    }

    fun clearCredentials() {
        viewModelScope.launch {
            dataStoreProvider.clearCredentials()
            _myProfileState.update { _myProfileState.value.copy(credentialsIsCleared = true) }
        }
    }

    override fun onCleared() {
        Log.d("AAA", "onCleared: ${this.javaClass.simpleName}")
        super.onCleared()
    }

    companion object {
        fun factory(dataStoreProvider: DataStoreProvider): ViewModelProvider.Factory {
            return viewModelFactory {
                initializer {
                    MyProfileViewModel(
                        dataStoreProvider = dataStoreProvider,
                        savedStateHandle = createSavedStateHandle()
                    )
                }
            }
        }
    }
}