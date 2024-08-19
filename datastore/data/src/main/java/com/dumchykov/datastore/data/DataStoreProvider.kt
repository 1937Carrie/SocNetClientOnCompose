package com.dumchykov.datastore.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.dumchykov.datastore.data.DataStoreProvider.Companion.COMMON
import com.dumchykov.datastore.data.serializers.ContactSerializer
import com.dumchykov.socialnetworkdemo.webapi.domain.models.Contact
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = COMMON)
val Context.contactDataStore: DataStore<Contact> by dataStore(
    fileName = "contacts.json",
    serializer = ContactSerializer
)

class DataStoreProvider @Inject constructor(private val context: Context) {

    private suspend fun saveString(stringKey: String, stringValue: String) {
        context.dataStore.edit { preferences ->
            val key = stringPreferencesKey(stringKey)
            preferences[key] = stringValue
        }
    }

    private fun readString(stringKey: String): Flow<String> {
        val stringPreferences = stringPreferencesKey(stringKey)
        return context.dataStore.data.map { preferences ->
            preferences[stringPreferences].orEmpty()
        }
    }

    private fun readInt(intKey: String): Flow<Int> {
        val intPreferences = intPreferencesKey(intKey)
        return context.dataStore.data.map { preferences ->
            preferences[intPreferences] ?: -1
        }
    }

    private suspend fun saveInt(intKey: String, intValue: Int) {
        context.dataStore.edit { preferences ->
            val key = intPreferencesKey(intKey)
            preferences[key] = intValue
        }
    }

    suspend fun saveCredentials(email: String, password: String) {
        saveString(KEY_EMAIL, email)
        saveString(KEY_PASSWORD, password)
    }

    suspend fun clearCredentials() {
        context.dataStore.edit { preferences ->
            preferences.clear()
        }
    }

    fun readEmail(): Flow<String> {
        return readString(KEY_EMAIL)
    }

    fun readPassword(): Flow<String> {
        return readString(KEY_PASSWORD)
    }

    fun readAccessToken(): Flow<String> {
        return readString(KEY_ACCESS_TOKEN)
    }

    suspend fun saveAccessToken(token: String) {
        saveString(KEY_ACCESS_TOKEN, token)
    }

    fun readRefreshToken(): Flow<String> {
        return readString(KEY_REFRESH_TOKEN)
    }

    suspend fun saveRefreshToken(token: String) {
        saveString(KEY_REFRESH_TOKEN, token)
    }

    // Save contact
    suspend fun saveContact(contact: Contact) {
        context.contactDataStore.updateData { contact }
    }

    // Retrieve contact
    fun getContact(): Flow<Contact> {
        return context.contactDataStore.data
    }

    companion object {
        const val COMMON = "common"
        const val KEY_EMAIL = "email"
        const val KEY_PASSWORD = "password"
        const val KEY_ACCESS_TOKEN = "access_token"
        const val KEY_REFRESH_TOKEN = "refresh_token"
    }
}
