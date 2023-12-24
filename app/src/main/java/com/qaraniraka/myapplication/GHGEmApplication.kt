package com.qaraniraka.myapplication

import android.app.Application
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.qaraniraka.myapplication.data.AppContainer
import com.qaraniraka.myapplication.data.AppDataContainer
import kotlin.reflect.typeOf

class GHGEmApplication: Application() {
    /**
     * AppContainer instance used by the rest of classes to obtain dependencies
     */
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        Log.w("GHGEmApplication", "onCreate")
        container = AppDataContainer(this)

        Log.w("GHGEmApplication", container.toString())
    }
}
fun CreationExtras.ghgEmApplication(): GHGEmApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as GHGEmApplication)