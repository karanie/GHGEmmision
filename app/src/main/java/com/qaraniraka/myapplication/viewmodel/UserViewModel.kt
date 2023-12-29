package com.qaraniraka.myapplication.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.qaraniraka.myapplication.model.PostSuccess
import com.qaraniraka.myapplication.model.UserCheckEmailData
import com.qaraniraka.myapplication.model.UserData
import com.qaraniraka.myapplication.model.UserLoginPostData
import com.qaraniraka.myapplication.model.UserLogoutPostData
import com.qaraniraka.myapplication.model.UserRegistrationPostData
import com.qaraniraka.myapplication.model.UserVerifyPostData
import com.qaraniraka.myapplication.model.VerifySuccess
import com.qaraniraka.myapplication.network.GHGEmBackendApi
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class UserViewModel : ViewModel() {
    var userUiState: UserUiState by mutableStateOf(UserUiState.Idle)
        private set

    fun registerUser(userRegistrationPostData: UserRegistrationPostData) {
        viewModelScope.launch {
            userUiState = UserUiState.Loading
            userUiState = try {
                UserUiState.RegisterSuccess(
                    GHGEmBackendApi.service.registerUser(
                        userRegistrationPostData
                    )
                )
            } catch (e: IOException) {
                UserUiState.Error
            } catch (e: HttpException) {
                UserUiState.Error
            } catch (e: Throwable) {
                UserUiState.Error
            }
        }
    }

    fun isEmailAvailable(email: String) {
        viewModelScope.launch {
            userUiState = UserUiState.Loading
            userUiState = try {
                UserUiState.EmailAvaiable(
                    GHGEmBackendApi.service.getUserByEmail(email) != UserCheckEmailData(email)
                )
            } catch (e: IOException) {
                Log.w("UserViewModel", e)
                UserUiState.Error
            } catch (e: HttpException) {
                Log.w("UserViewModel", e)
                UserUiState.Error
            } catch (e: Throwable) {
                Log.w("UserViewModel", e)
                UserUiState.Error
            }
        }
    }

    fun loginUser(userLoginPostData: UserLoginPostData) {
        viewModelScope.launch {
            userUiState = UserUiState.Loading
            userUiState = try {
                UserUiState.LoginSuccess(
                    GHGEmBackendApi.service.loginUser(
                        userLoginPostData
                    )
                )
            } catch (e: IOException) {
                UserUiState.Error
            } catch (e: HttpException) {
                UserUiState.Error
            } catch (e: Throwable) {
                UserUiState.Error
            }
        }
    }

    fun logoutUser(userLogoutPostData: UserLogoutPostData) {
        viewModelScope.launch {
            userUiState = UserUiState.Loading
            userUiState = try {
                GHGEmBackendApi.service.logoutUser(
                    userLogoutPostData
                )
                UserUiState.LogoutSuccess
            } catch (e: IOException) {
                UserUiState.Error
            } catch (e: HttpException) {
                UserUiState.Error
            } catch (e: Throwable) {
                UserUiState.Error
            }
        }
    }

    fun verifyUser(userVerifyPostData: UserVerifyPostData) {
        viewModelScope.launch {
            userUiState = UserUiState.Loading
            userUiState = try {
                Log.w("UserViewModel", userUiState.toString())
                UserUiState.LoginSuccess(
                    GHGEmBackendApi.service.verifyUser(
                        userVerifyPostData
                    )
                )
            } catch (e: IOException) {
                UserUiState.Error
            } catch (e: HttpException) {
                UserUiState.Error
            } catch (e: Throwable) {
                UserUiState.Error
            }
        }
    }


    fun getUserDataBySession(session: String) {
        viewModelScope.launch {
            userUiState = UserUiState.Loading
            userUiState = try {
                UserUiState.UserDataSuccess(
                    GHGEmBackendApi.service.getUserBySession(session)
                )
            } catch (e: IOException) {
                Log.w("UserViewModel", e)
                UserUiState.Error
            } catch (e: HttpException) {
                Log.w("UserViewModel", e)
                UserUiState.Error
            } catch (e: Throwable) {
                Log.w("UserViewModel", e)
                UserUiState.Error
            }
        }

    }
}

sealed interface UserUiState {
    data class RegisterSuccess(val data: PostSuccess) : UserUiState
    data class LoginSuccess(val data: VerifySuccess) : UserUiState
    data class EmailAvaiable(val data: Boolean) : UserUiState
    data class UserDataSuccess(val data: UserData) : UserUiState
    object LogoutSuccess : UserUiState
    object Error : UserUiState
    object Loading : UserUiState
    object Idle : UserUiState
}