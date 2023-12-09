package com.qaraniraka.myapplication.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun MakanScreen() {
    var jenisBensinExpanded by remember { mutableStateOf(false) }
    var jenisBensinSelected by remember { mutableStateOf("") }
    val jenisBensin = arrayOf(
        "Reguler",
        "Premium",
        "Diesel",
        "Ethanol",
        "Natural"
    )

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
                    value = "Ukuran Engine",
                    onValueChange = { /* TODO */ },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = "Jumlah Cylinders",
                    onValueChange = { /* TODO */ },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = "Fuel Consumption",
                    onValueChange = { /* TODO */ },
                    modifier = Modifier.fillMaxWidth()
                )
                ExposedDropdownMenuBox(
                    expanded = jenisBensinExpanded,
                    onExpandedChange = { jenisBensinExpanded = !jenisBensinExpanded }
                ) {
                    OutlinedTextField(
                        value = "Jenis Bensin",
                        onValueChange = { /* TODO */ },
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
                    value = "Jarak Berkendara",
                    onValueChange = { /* TODO */ },
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