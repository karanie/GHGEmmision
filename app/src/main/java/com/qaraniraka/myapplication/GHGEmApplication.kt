package com.qaraniraka.myapplication

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.qaraniraka.myapplication.data.AppContainer
import com.qaraniraka.myapplication.data.AppDataContainer
import com.qaraniraka.myapplication.data.UserSessionPreferencesRepository

class GHGEmApplication: Application() {
    /**
     * AppContainer instance used by the rest of classes to obtain dependencies
     */
    lateinit var container: AppContainer
    lateinit var userSessionPreferencesRepository: UserSessionPreferencesRepository

    override fun onCreate() {
        super.onCreate()
        Log.w("GHGEmApplication", "onCreate")
        container = AppDataContainer(this)
        userSessionPreferencesRepository = UserSessionPreferencesRepository(dataStore)

        Log.w("GHGEmApplication", container.toString())
    }
}
fun CreationExtras.ghgEmApplication(): GHGEmApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as GHGEmApplication)

private const val USER_SESSION_PREFERENCE_NAME = "user_session_preference"
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = USER_SESSION_PREFERENCE_NAME
)