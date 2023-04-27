package com.stanislavdumchykov.socialnetworkclient.data

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.stanislavdumchykov.socialnetworkclient.data.utils.ext.dataStore
import com.stanislavdumchykov.socialnetworkclient.domain.repository.StorageRepository
import com.stanislavdumchykov.socialnetworkclient.presentation.utils.Constants
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class StorageRepositoryImpl @Inject constructor(@ApplicationContext private val context: Context) :
    StorageRepository {

    override val getEmailToken: Flow<String> = context.dataStore.data.map { preferences ->
        preferences[stringPreferencesKey(Constants.PREFERENCES_EMAIL)] ?: ""
    }
    override val getPasswordToken: Flow<String> = context.dataStore.data.map { preferences ->
        preferences[stringPreferencesKey(Constants.PREFERENCES_PASSWORD)] ?: ""
    }

    override suspend fun saveString(key: String, value: String) {
        context.dataStore.edit { preferences ->
            preferences[stringPreferencesKey(key)] = value
        }
    }
}