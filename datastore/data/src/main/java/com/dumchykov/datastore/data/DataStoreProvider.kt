package com.dumchykov.datastore.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.dumchykov.datastore.data.DataStoreProvider.Companion.CREDENTIALS
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = CREDENTIALS)

class DataStoreProvider(private val context: Context) {

    private suspend fun saveString(stringKey: String, stringValue: String) {
        context.dataStore.edit { credentials ->
            val key = stringPreferencesKey(stringKey)
            credentials[key] = stringValue
        }
    }

    private fun readString(stringKey: String): Flow<String> {
        val stringPreferences = stringPreferencesKey(stringKey)
        return context.dataStore.data.map { credentials ->
            credentials[stringPreferences] ?: ""
        }
    }

    suspend fun saveCredentials(email: String, password: String) {
        saveString(KEY_EMAIL, email)
        saveString(KEY_PASSWORD, password)
    }

    suspend fun clearCredentials() {
        context.dataStore.edit { credentials ->
            credentials.clear()
        }
    }

    fun readEmail(): Flow<String> {
        return readString(KEY_EMAIL)
    }

    fun readPassword(): Flow<String> {
        return readString(KEY_PASSWORD)
    }

    companion object {
        const val CREDENTIALS = "credentials"
        const val KEY_EMAIL = "email"
        const val KEY_PASSWORD = "password"
    }
}