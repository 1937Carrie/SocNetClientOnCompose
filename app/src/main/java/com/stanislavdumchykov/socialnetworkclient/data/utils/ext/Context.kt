package com.stanislavdumchykov.socialnetworkclient.data.utils.ext

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.stanislavdumchykov.socialnetworkclient.presentation.utils.Constants

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    Constants.PREFERENCES_DATASTORE
)