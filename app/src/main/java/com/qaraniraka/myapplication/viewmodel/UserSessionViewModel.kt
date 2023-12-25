package com.qaraniraka.myapplication.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.qaraniraka.myapplication.GHGEmApplication
import com.qaraniraka.myapplication.data.UserSessionPreferencesRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class UserSessionViewModel(private val userSessionPreferencesRepository: UserSessionPreferencesRepository) :
    ViewModel() {
    val uiState: StateFlow<UserSessionUiState> = userSessionPreferencesRepository.userSession.map {
        UserSessionUiState(it)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = UserSessionUiState()
    )
    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as GHGEmApplication)
                UserSessionViewModel(application.userSessionPreferencesRepository)
            }
        }
    }

    fun saveUserSession(userSession: String) {
        viewModelScope.launch {
            userSessionPreferencesRepository.saveUserSession(userSession)
        }
    }

    fun clearUserSession() {
        viewModelScope.launch {
            userSessionPreferencesRepository.saveUserSession("")
        }
    }
}

data class UserSessionUiState(
    val userSession: String = ""
)