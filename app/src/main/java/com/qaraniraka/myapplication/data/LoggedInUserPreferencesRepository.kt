package com.qaraniraka.myapplication.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class LoggedInUserPreferencesRepository(
    private val dataStore: DataStore<Preferences>
) {
    private companion object {
        val USER_SESSION = stringPreferencesKey("user_session")
    }

    val userSession: Flow<String> = dataStore.data.map { preferences ->
        preferences[USER_SESSION] ?: ""
    }

    suspend fun saveUserSession(userSession: String) {
        dataStore.edit { preferences ->
            preferences[USER_SESSION]
        }
    }
}