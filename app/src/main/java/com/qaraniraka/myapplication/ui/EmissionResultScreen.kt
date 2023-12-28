package com.qaraniraka.myapplication.ui

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.qaraniraka.myapplication.MainActivity
import com.qaraniraka.myapplication.viewmodel.ActivityUiState
import com.qaraniraka.myapplication.viewmodel.ActivityViewModel

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun EmissionResultScreen(resultId: Int? = 0) {
    val context = LocalContext.current
    val activityViewModel: ActivityViewModel = viewModel()
    if ((resultId != null) and (activityViewModel.activityUiState is ActivityUiState.Idle)) {
        if (resultId != null) {
            activityViewModel.getActivityResultById(resultId)
        }
    }
    var emission: Double? = null
    var message: String? = null
    if (activityViewModel.activityUiState is ActivityUiState.ActivityResultsSuccess) {
        if (resultId != null) {
            emission =
                (activityViewModel.activityUiState as ActivityUiState.ActivityResultsSuccess).data.emission
            message =
                (activityViewModel.activityUiState as ActivityUiState.ActivityResultsSuccess).data.message
        }
    }
    Surface {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(120.dp, Alignment.CenterVertically),
                modifier = Modifier.fillMaxHeight(0.75f)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .width(240.dp)
                        .height(240.dp)
                        .clip(CircleShape)
                        .background(color = MaterialTheme.colorScheme.inversePrimary)
                ) {
                    if (emission != null) {
                        Text(
                            text = "${String.format("%.2f", emission)}g",
                            style = MaterialTheme.typography.displayLarge
                        )
                    }
                }
                Text(
                    text = message ?: "", style = MaterialTheme.typography.titleLarge
                )
            }
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier
                    .padding(16.dp)
            ) {
                OutlinedButton(
                    onClick = { /*TODO*/ },
                    modifier = Modifier
                        .height(56.dp)
                        .fillMaxWidth()
                ) {
                    Text(text = "Bagikan", style = MaterialTheme.typography.titleMedium)
                }
                Button(
                    onClick = {
                        val intent = Intent(context, MainActivity::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                        context.startActivity(intent)
                    },
                    modifier = Modifier
                        .height(56.dp)
                        .fillMaxWidth()
                ) {
                    Text(text = "Kembali", style = MaterialTheme.typography.titleMedium)
                }
            }
        }
    }
}