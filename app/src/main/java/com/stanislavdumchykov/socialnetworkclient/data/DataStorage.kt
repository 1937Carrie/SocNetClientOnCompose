package com.stanislavdumchykov.socialnetworkclient.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.stanislavdumchykov.socialnetworkclient.util.DATA_STORE_NAME
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = DATA_STORE_NAME)

class DataStorage(private val context: Context) {

    fun readString(key: String): Flow<String> {
        val stringPreferencesKey = stringPreferencesKey(key)
        return context.dataStore.data.map { preferences ->
            preferences[stringPreferencesKey] ?: ""
        }
    }

    suspend fun saveString(key: String, value: String) {
        val stringPreferencesKey = stringPreferencesKey(key)
        context.dataStore.edit { settings ->
            settings[stringPreferencesKey] = value
        }
    }
}