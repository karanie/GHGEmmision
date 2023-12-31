package com.qaraniraka.myapplication.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.qaraniraka.myapplication.model.ActivityHistory
import com.qaraniraka.myapplication.model.ActivityPostData
import com.qaraniraka.myapplication.model.ActivityResults
import com.qaraniraka.myapplication.model.EmissionList
import com.qaraniraka.myapplication.model.PostSuccess
import com.qaraniraka.myapplication.network.GHGEmBackendApi
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class ActivityViewModel : ViewModel() {
    var activityUiState: ActivityUiState by mutableStateOf(ActivityUiState.Idle)
        private set

    fun postActivity(activityPostData: ActivityPostData) {
        viewModelScope.launch {
            activityUiState = ActivityUiState.Loading
            activityUiState = try {
                ActivityUiState.PostActivitySuccess(
                    GHGEmBackendApi.service.postActivity(
                        activityPostData
                    )
                )
            } catch (e: IOException) {
                ActivityUiState.Error
            } catch (e: HttpException) {
                ActivityUiState.Error
            } catch (e: Throwable) {
                ActivityUiState.Error
            }
        }
    }

    fun getActivityResultById(activityId: Int) {
        viewModelScope.launch {
            activityUiState = ActivityUiState.Loading
            activityUiState = try {
                ActivityUiState.ActivityResultsSuccess(
                    GHGEmBackendApi.service.getActivityResultById(
                        activityId
                    )
                )
            } catch (e: IOException) {
                ActivityUiState.Error
            } catch (e: HttpException) {
                ActivityUiState.Error
            } catch (e: Throwable) {
                ActivityUiState.Error
            }
        }
    }

    fun getActivityHistoryBySession(session: String) {
        viewModelScope.launch {
            activityUiState = ActivityUiState.Loading
            activityUiState = try {
                ActivityUiState.ActivityHistorySuccess(
                    GHGEmBackendApi.service.getActivityBySession(
                        session
                    )
                )
            } catch (e: IOException) {
                ActivityUiState.Error
            } catch (e: HttpException) {
                ActivityUiState.Error
            } catch (e: Throwable) {
                ActivityUiState.Error
            }
        }
    }

    fun getEmission(session: String, interval: String) {
        viewModelScope.launch {
            activityUiState = ActivityUiState.Loading
            activityUiState = try {
                ActivityUiState.EmissionSuccess(
                    GHGEmBackendApi.service.getEmission(session, interval)
                )
            } catch (e: IOException) {
                ActivityUiState.Error
            } catch (e: HttpException) {
                ActivityUiState.Error
            } catch (e: Throwable) {
                ActivityUiState.Error
            }
        }
    }
}

sealed interface ActivityUiState {
    data class PostActivitySuccess(val data: PostSuccess) : ActivityUiState
    data class ActivityResultsSuccess(val data: ActivityResults) : ActivityUiState
    data class ActivityHistorySuccess(val data: ActivityHistory) : ActivityUiState
    data class EmissionSuccess(val data: EmissionList) : ActivityUiState
    object Error : ActivityUiState
    object Loading : ActivityUiState
    object Idle : ActivityUiState
}