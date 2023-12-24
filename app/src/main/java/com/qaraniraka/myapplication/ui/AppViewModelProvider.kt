package com.qaraniraka.myapplication.ui

import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.qaraniraka.myapplication.GHGEmApplication
import com.qaraniraka.myapplication.ghgEmApplication
import com.qaraniraka.myapplication.viewmodel.UserViewModel

object AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            UserViewModel(ghgEmApplication().container.usersRepository)
        }
    }
}