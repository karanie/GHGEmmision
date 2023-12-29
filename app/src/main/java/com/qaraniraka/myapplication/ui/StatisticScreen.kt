package com.qaraniraka.myapplication.ui

import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.patrykandpatrick.vico.compose.axis.horizontal.rememberBottomAxis
import com.patrykandpatrick.vico.compose.axis.vertical.rememberStartAxis
import com.patrykandpatrick.vico.compose.chart.Chart
import com.patrykandpatrick.vico.compose.chart.column.columnChart
import com.patrykandpatrick.vico.core.entry.entryModelOf
import com.qaraniraka.myapplication.MainActivity
import com.qaraniraka.myapplication.viewmodel.ActivityUiState
import com.qaraniraka.myapplication.viewmodel.ActivityViewModel
import com.qaraniraka.myapplication.viewmodel.UserSessionViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun StatisticScreen() {
    val context = LocalContext.current

    val userSessionViewModel: UserSessionViewModel = viewModel(
        factory = UserSessionViewModel.Factory
    )
    val userSession = userSessionViewModel.uiState.collectAsState().value.userSession
    val activityViewModelForTheLastWeek: ActivityViewModel = viewModel()
    val activityViewModelForTheLastMonth: ActivityViewModel = viewModel()

    if (userSession.isNotEmpty()
        and (activityViewModelForTheLastWeek.activityUiState is ActivityUiState.Idle)
    ) {
        activityViewModelForTheLastWeek.getEmission(userSession, "week")
    }
    if (userSession.isNotEmpty()
        and (activityViewModelForTheLastMonth.activityUiState is ActivityUiState.Idle)
    ) {
        activityViewModelForTheLastMonth.getEmission(userSession, "month")
    }

    var chartEntryModelWeek by remember { mutableStateOf(entryModelOf(0f)) }
    if (activityViewModelForTheLastWeek.activityUiState is ActivityUiState.EmissionSuccess) {
        chartEntryModelWeek = entryModelOf(
            *(activityViewModelForTheLastWeek.activityUiState as ActivityUiState.EmissionSuccess).data.data
        )
    }

    var chartEntryModelMonth by remember { mutableStateOf(entryModelOf(0f)) }
    if (activityViewModelForTheLastMonth.activityUiState is ActivityUiState.EmissionSuccess) {
        chartEntryModelMonth = entryModelOf(
            *(activityViewModelForTheLastMonth.activityUiState as ActivityUiState.EmissionSuccess).data.data
        )
    }

    Surface {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Statistik") },
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
            },
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier
                    .padding(it)
                    .padding(16.dp)
            ) {
                OutlinedCard {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "Emisi dalam >7 Hari",
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier.padding(bottom = 16.dp)
                        )
                        Chart(
                            chart = columnChart(),
                            model = chartEntryModelWeek,
                            startAxis = rememberStartAxis(),
                            bottomAxis = rememberBottomAxis()
                        )
                    }
                }
                OutlinedCard {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "Emisi dalam >30 Hari",
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier.padding(bottom = 16.dp)
                        )
                        Chart(
                            chart = columnChart(),
                            model = chartEntryModelMonth,
                            startAxis = rememberStartAxis(),
                            bottomAxis = rememberBottomAxis()
                        )
                    }
                }
            }
        }
    }
}
