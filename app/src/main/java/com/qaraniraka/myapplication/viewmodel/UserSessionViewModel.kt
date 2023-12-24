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
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class UserSessionViewModel(private val userSessionPreferencesRepository: UserSessionPreferencesRepository) :
    ViewModel() {

    private val _uiState = MutableStateFlow(UserSessionUiState())
    val uiState: StateFlow<UserSessionUiState> = _uiState

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