package com.stanislavdumchykov.socialnetworkclient.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.stanislavdumchykov.socialnetworkclient.domain.utils.Constants
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserStore @Inject constructor(@ApplicationContext private val context: Context) {
    companion object {
        private val Context.dataStoreEmail: DataStore<Preferences> by preferencesDataStore(
            Constants.PREFERENCES_EMAIL
        )
        private val Context.dataStorePassword: DataStore<Preferences> by preferencesDataStore(
            Constants.PREFERENCES_PASSWORD
        )
    }

    val getEmailToken: Flow<String> = context.dataStoreEmail.data.map { preferences ->
        preferences[stringPreferencesKey(Constants.PREFERENCES_EMAIL)] ?: ""
    }
    val getPasswordToken: Flow<String> = context.dataStorePassword.data.map { preferences ->
        preferences[stringPreferencesKey(Constants.PREFERENCES_PASSWORD)] ?: ""
    }

    suspend fun saveToken(key: String, value: String) {
        when (key) {
            Constants.PREFERENCES_EMAIL -> {
                context.dataStoreEmail.edit { preferences ->
                    preferences[stringPreferencesKey(key)] = value
                }
            }
            Constants.PREFERENCES_PASSWORD -> {
                context.dataStorePassword.edit { preferences ->
                    preferences[stringPreferencesKey(key)] = value
                }
            }
        }
    }
}