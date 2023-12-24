package com.qaraniraka.myapplication.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.qaraniraka.myapplication.data.User
import com.qaraniraka.myapplication.data.UsersRepository

class UserViewModel(private val usersRepository: UsersRepository): ViewModel() {
    var userUiState by mutableStateOf(UserUiState())
        private set

    suspend fun registerUser() {
        if (validateInput()) {
            usersRepository.insertUser(userUiState.toUser())
        }
    }

    private fun validateInput(uiState: UserUiState = userUiState): Boolean {
        return with(uiState) {
            password.length >= 8
        }
    }

    fun updateUiState(state: UserUiState) {
        userUiState = UserUiState(
            userId = state.userId,
            email = state.email,
            password = state.password,
            fullName = state.fullName,
            birthday = state.birthday,
        )
    }
}

data class UserUiState(
    var userId: Int = 0,
    var email: String = "",
    var password: String = "",
    var fullName: String = "",
    var birthday: Int = 0
)

fun UserUiState.toUser(): User = User(
    userId = userId,
    email = email,
    password = password,
    fullName = fullName,
    birthday = birthday
)