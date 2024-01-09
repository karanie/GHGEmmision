package com.qaraniraka.myapplication.ui

import android.widget.Toast
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
import androidx.compose.ui.platform.LocalContext
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
    val context = LocalContext.current
    val userSessionViewModel: UserSessionViewModel = viewModel(
        factory = UserSessionViewModel.Factory
    )
    val userSession = userSessionViewModel.uiState.collectAsState().value.userSession
    val activityViewModel: ActivityViewModel = viewModel()
    val gson = Gson()

    var ukuranEngine by remember { mutableStateOf("") }
    var jumlahCylinders by remember { mutableStateOf("") }
    var fuelConsumption by remember { mutableStateOf("") }
    var jenisBensinExpanded by remember { mutableStateOf(false) }
    var jenisBensinSelected by remember { mutableStateOf("") }
    val jenisBensin = arrayOf(
        "Reguler",
        "Premium",
        "Diesel",
        "Ethanol",
        "Natural"
    )
    var jarakBerkendara by remember { mutableStateOf("") }

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
                    value = ukuranEngine,
                    onValueChange = { ukuranEngine = it },
                    label = {
                        Text("Ukuran Engine")
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = jumlahCylinders,
                    onValueChange = { jumlahCylinders = it },
                    label = {
                        Text("Jumlah Cylinders")
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = fuelConsumption,
                    onValueChange = { fuelConsumption = it },
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
                    value = jarakBerkendara,
                    onValueChange = { jarakBerkendara = it },
                    label = {
                        Text("Jarak Berkendara")
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth()
                )
            }
            Button(
                onClick = {
                    try {
                        if (userSession.isNotEmpty()) {
                            activityViewModel.postActivity(
                                ActivityPostData(
                                    userSession,
                                    "berkendara",
                                    gson.toJson(
                                        ActivityBerkendaraDetail(
                                            ukuranEngine.toDouble(),
                                            jumlahCylinders.toInt(),
                                            fuelConsumption.toDouble(),
                                            jenisBensinSelected,
                                            jarakBerkendara.toDouble()
                                        )
                                    )
                                )
                            )
                        }
                    } catch (e: NumberFormatException) {
                        // TODO: This doesn't show up for some reason
                        Toast.makeText(context, "Inputan angka tidak benar", Toast.LENGTH_SHORT)
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