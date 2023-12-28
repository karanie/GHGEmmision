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
import com.qaraniraka.myapplication.model.ActivityBerkendaraDetail
import com.qaraniraka.myapplication.model.ActivityPostData
import com.qaraniraka.myapplication.viewmodel.ActivityUiState
import com.qaraniraka.myapplication.viewmodel.ActivityViewModel
import com.qaraniraka.myapplication.viewmodel.UserSessionViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun BerkendaraScreen(
    onRecordSaved: (insertId: Int) -> Unit = {}
) {
    val userSessionViewModel: UserSessionViewModel = viewModel(
        factory = UserSessionViewModel.Factory
    )
    val userSession = userSessionViewModel.uiState.collectAsState().value.userSession
    val activityViewModel: ActivityViewModel = viewModel()
    val gson = Gson()

    var ukuranEngine by remember { mutableStateOf<Double?>(null) }
    var jumlahCylinders by remember { mutableStateOf<Int?>(null) }
    var fuelConsumption by remember { mutableStateOf<Double?>(null) }
    var jenisBensinExpanded by remember { mutableStateOf(false) }
    var jenisBensinSelected by remember { mutableStateOf("") }
    val jenisBensin = arrayOf(
        "Reguler",
        "Premium",
        "Diesel",
        "Ethanol",
        "Natural"
    )
    var jarakBerkendara by remember { mutableStateOf<Double?>(null) }

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
                text = "Tambah Aktivitas Berkendara",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier
                    .padding(bottom = 32.dp)
                    .align(Alignment.Start)
            )
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                OutlinedTextField(
                    value = ukuranEngine?.toString() ?: "",
                    onValueChange = { ukuranEngine = it.toDouble() },
                    label = {
                        Text("Ukuran Engine")
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = jumlahCylinders?.toString() ?: "",
                    onValueChange = { jumlahCylinders = it.toInt() },
                    label = {
                        Text("Jumlah Cylinders")
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = fuelConsumption?.toString() ?: "",
                    onValueChange = { fuelConsumption = it.toDouble() },
                    label = {
                        Text("Fuel Consumption per 100 km")
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth()
                )
                ExposedDropdownMenuBox(
                    expanded = jenisBensinExpanded,
                    onExpandedChange = { jenisBensinExpanded = it }
                ) {
                    OutlinedTextField(
                        value = jenisBensinSelected,
                        onValueChange = { },
                        label = {
                            Text("Jenis Bensin")
                        },
                        readOnly = true,
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = jenisBensinExpanded) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor()
                    )
                    ExposedDropdownMenu(
                        expanded = jenisBensinExpanded,
                        onDismissRequest = { jenisBensinExpanded = false }) {
                        jenisBensin.forEach{
                            DropdownMenuItem(text = { Text(it) }, onClick = {
                                jenisBensinSelected = it
                                jenisBensinExpanded = false
                            })
                        }
                    }
                }

                OutlinedTextField(
                    value = jarakBerkendara?.toString() ?: "",
                    onValueChange = { jarakBerkendara = it.toDouble() },
                    label = {
                        Text("Jarak Berkendara")
                    },
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
                                "berkendara",
                                gson.toJson(
                                    ActivityBerkendaraDetail(
                                        ukuranEngine ?: 0.0,
                                        jumlahCylinders ?: 0,
                                        fuelConsumption ?: 0.0,
                                        jenisBensinSelected,
                                        jarakBerkendara ?: 0.0
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