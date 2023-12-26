package com.qaraniraka.myapplication

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.qaraniraka.myapplication.data.UserSessionPreferencesRepository

class GHGEmApplication: Application() {
    /**
     * AppContainer instance used by the rest of classes to obtain dependencies
     */
    lateinit var userSessionPreferencesRepository: UserSessionPreferencesRepository

    override fun onCreate() {
        super.onCreate()
        userSessionPreferencesRepository = UserSessionPreferencesRepository(dataStore)
    }
}

private const val USER_SESSION_PREFERENCE_NAME = "user_session_preference"
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = USER_SESSION_PREFERENCE_NAME
)