package com.qaraniraka.myapplication.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.gson.Gson
import com.qaraniraka.myapplication.model.ActivityMakanDetail
import com.qaraniraka.myapplication.model.ActivityPostData
import com.qaraniraka.myapplication.viewmodel.ActivityUiState
import com.qaraniraka.myapplication.viewmodel.ActivityViewModel
import com.qaraniraka.myapplication.viewmodel.UserSessionViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun MakanScreen(
    onRecordSaved: (insertId: Int) -> Unit = {}
) {
    val activityViewModel: ActivityViewModel = viewModel()
    val userSessionViewModel: UserSessionViewModel = viewModel(
        factory = UserSessionViewModel.Factory
    )
    val userSession = userSessionViewModel.uiState.collectAsState().value.userSession
    val gson = Gson()

    var beratMakanan by remember { mutableStateOf<Double?>(null) }
    var bahanDasarExpanded by remember { mutableStateOf(false) }
    var bahanDasarSelected by remember { mutableStateOf("") }
    val bahanDasar = arrayOf(
        "Apples",
        "Bananas",
        "Barley",
        "Beef (beef herd)",
        "Beef (dairy herd)",
        "Beet Sugar",
        "Berries & Grapes"
        /* TODO: Implement long selection list*/
    )

    if (activityViewModel.activityUiState is ActivityUiState.PostActivitySuccess) {
        val insertId = (activityViewModel.activityUiState as ActivityUiState.PostActivitySuccess).data.insertId
        onRecordSaved(insertId)
    }

    Surface {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(32.dp)
                .fillMaxSize()
        ) {
            Text(
                text = "Tambah Aktivitas Makan",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier
                    .padding(bottom = 32.dp)
                    .align(Alignment.Start)
            )
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                ExposedDropdownMenuBox(
                    expanded = bahanDasarExpanded,
                    onExpandedChange = { bahanDasarExpanded = !bahanDasarExpanded }
                ) {
                    OutlinedTextField(
                        value = bahanDasarSelected,
                        label = { Text("Bahan Dasar") },
                        onValueChange = { },
                        readOnly = true,
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = bahanDasarExpanded) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor()
                    )
                    ExposedDropdownMenu(
                        expanded = bahanDasarExpanded,
                        onDismissRequest = { bahanDasarExpanded = false }) {
                        bahanDasar.forEach {
                            DropdownMenuItem(text = { Text(it) }, onClick = {
                                bahanDasarSelected = it
                                bahanDasarExpanded = false
                            })
                        }
                    }
                }

                OutlinedTextField(
                    value = beratMakanan?.toString() ?: "",
                    label = {
                        Text("Berat Makanan (gram)")
                    },
                    onValueChange = { beratMakanan = it.toDouble() },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth()
                )
            }
            Button(
                onClick = {
                    if (userSession.isNotEmpty()) {
                        activityViewModel.postActivity(
                            ActivityPostData(
                                userSession,
                                "makan",
                                gson.toJson(
                                    ActivityMakanDetail(
                                        bahanDasarSelected,
                                        (beratMakanan ?: 0.0)/1000.0
                                    )
                                )
                            )

                        )
                    }
                },
                modifier = Modifier
                    .padding(top = 16.dp)
                    .align(Alignment.End)
            ) {
                Text(text = "Tambahkan")
            }
        }
    }
}