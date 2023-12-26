package com.qaraniraka.myapplication.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.qaraniraka.myapplication.model.FoobarList
import com.qaraniraka.myapplication.model.FoobarPostData
import com.qaraniraka.myapplication.model.PostSuccess
import com.qaraniraka.myapplication.network.GHGEmBackendApi
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class FoobarViewModel : ViewModel() {
    var foobarUiState: FoobarUiState by mutableStateOf(FoobarUiState.Loading)
        private set

    fun getFoobarData() {
        viewModelScope.launch {
            foobarUiState = FoobarUiState.Loading
            foobarUiState = try {
                FoobarUiState.Success(GHGEmBackendApi.service.getFoobar())
            } catch (e: IOException) {
                FoobarUiState.Error
            } catch (e: HttpException) {
                FoobarUiState.Error
            }
        }
    }

    fun postFoobar() {
        viewModelScope.launch {
            foobarUiState = FoobarUiState.Loading
            foobarUiState = try {
                FoobarUiState.InsertSuccess(GHGEmBackendApi.service.postFoobar(FoobarPostData("test1", "test2")))
            } catch (e: IOException) {
                Log.w("FoobarViewModel.kt", e.toString())
                FoobarUiState.Error
            } catch (e: HttpException) {
                Log.w("FoobarViewModel.kt", e.toString())
                FoobarUiState.Error
            }
        }
    }
}

sealed interface FoobarUiState {
    data class Success(val data: FoobarList) : FoobarUiState
    data class InsertSuccess(val data: PostSuccess) : FoobarUiState
    object Error : FoobarUiState
    object Loading : FoobarUiState
}