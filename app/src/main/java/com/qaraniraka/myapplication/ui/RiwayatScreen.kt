package com.qaraniraka.myapplication.ui

import android.content.Intent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.qaraniraka.myapplication.MainActivity
import com.qaraniraka.myapplication.model.ActivityHistoryData
import com.qaraniraka.myapplication.viewmodel.ActivityUiState
import com.qaraniraka.myapplication.viewmodel.ActivityViewModel
import com.qaraniraka.myapplication.viewmodel.UserSessionViewModel
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showSystemUi = true, showBackground = true)
@Composable
fun RiwayatScreen(
    onHistoryItemClick: (data: ActivityHistoryData) -> Unit = {}
) {
    val userSessionViewModel: UserSessionViewModel = viewModel(
        factory = UserSessionViewModel.Factory
    )
    val userSession = userSessionViewModel.uiState.collectAsState().value.userSession
    val activityViewModel: ActivityViewModel = viewModel()

    if (activityViewModel.activityUiState is ActivityUiState.Idle) {
        if (userSession.isNotEmpty()) {
            activityViewModel.getActivityHistoryBySession(userSession)
        }
    }

    val context = LocalContext.current
    var historyData: List<ActivityHistoryData>? = null
    if (activityViewModel.activityUiState is ActivityUiState.ActivityHistorySuccess) {
        historyData = (activityViewModel.activityUiState as ActivityUiState.ActivityHistorySuccess)
            .data.data
    }

    val dateFormatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME

    Surface {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Riwayat") },
                    navigationIcon = {
                        IconButton(onClick = {
                            val intent = Intent(context, MainActivity::class.java)
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                            context.startActivity(intent)
                        }) {
                            Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = null)
                        }
                    }
                )
            }
        ) {
            Column(Modifier.padding(it)) {
                if (historyData != null) {
                    historyData.forEach {
                        ListItem(
                            headlineContent = {
                                Text(it.activityType)
                            },
                            supportingContent = {
                                Text("${String.format("%.2f", it.emission)}g")
                            },
                            trailingContent = {
                                val ctime = LocalDateTime.parse(it.ctime, dateFormatter)
                                Column(
                                    horizontalAlignment = Alignment.End
                                ) {
                                    Text("${ctime.month.name} ${ctime.dayOfMonth}")
                                    Text("${String.format("%02d", ctime.hour)}.${String.format("%02d", ctime.minute)}")
                                }
                            },
                            modifier = Modifier
                                .clickable {
                                    onHistoryItemClick(it)
                                }
                        )
                    }
                }
            }
        }
    }
}