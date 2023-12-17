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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun BerkendaraScreen() {
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
                    onValueChange = { it -> ukuranEngine = it.toDouble() },
                    label = {
                        Text("Ukuran Engine")
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = ukuranEngine?.toString() ?: "",
                    onValueChange = { jumlahCylinders = it.toInt() },
                    label = {
                        Text("Jumlah Cylinders")
                    },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = fuelConsumption?.toString() ?: "",
                    onValueChange = { fuelConsumption = it.toDouble() },
                    label = {
                        Text("Fuel Consumption")
                    },
                    modifier = Modifier.fillMaxWidth()
                )
                ExposedDropdownMenuBox(
                    expanded = jenisBensinExpanded,
                    onExpandedChange = { jenisBensinExpanded = !jenisBensinExpanded }
                ) {
                    OutlinedTextField(
                        value = jenisBensinSelected,
                        onValueChange = { },
                        label = {
                            Text("Jenis Bensin")
                        },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = jenisBensinExpanded) },
                        modifier = Modifier.fillMaxWidth()
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
                    modifier = Modifier.fillMaxWidth()
                )
            }
            Button(
                onClick = { /*TODO*/ },
                modifier = Modifier
                    .padding(top = 16.dp)
                    .align(Alignment.End)
            ) {
                Text(text = "Tambahkan")
            }
        }
    }
}