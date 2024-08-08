package com.dumchykov.socialnetworkdemo.ui.screens.myprofile

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.dumchykov.datastore.data.DataStoreProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MyProfileViewModel(
    private val dataStoreProvider: DataStoreProvider,
) : ViewModel() {
    private val _myProfileState = MutableStateFlow(MyProfileState())
    val myProfileState get() = _myProfileState.asStateFlow()

    fun parseEmail(email: String) {
        val parsedName = email
            .substringBefore('@')
            .split('.')
            .fold("") { acc, s -> "$acc ${s.replaceFirstChar { it.uppercase() }} " }
            .trim()
        _myProfileState.update { _myProfileState.value.copy(name = parsedName) }
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
        fun factory(context: Context): ViewModelProvider.Factory {
            return viewModelFactory {
                initializer {
                    val dataStoreProvider = DataStoreProvider(context)
                    MyProfileViewModel(dataStoreProvider)
                }
            }
        }
    }
}