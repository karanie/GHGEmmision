package com.qaraniraka.myapplication.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun RegisterScreen() {
    Surface {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize().padding(64.dp)
        ) {
            Text(
                text = "Register",
                style = MaterialTheme.typography.displaySmall,
                modifier = Modifier
                    .padding(bottom = 64.dp)
                    .align(Alignment.Start)
            )
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(
                    16.dp,
                    alignment = Alignment.CenterVertically
                ),
            ) {
                OutlinedTextField(
                    value = "Nama Lengkap",
                    onValueChange = { /* TODO */ },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = "Email",
                    onValueChange = { /* TODO */ },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = "Tanggal Lahir",
                    onValueChange = { /* TODO */ },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = "Password",
                    onValueChange = { /* TODO */ },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = "Konfirmasi Password",
                    onValueChange = { /* TODO */ },
                    modifier = Modifier.fillMaxWidth()
                )
            }
            Button(
                onClick = { /*TODO*/ },
                modifier = Modifier
                    .width(120.dp)
                    .padding(top = 16.dp)
                    .align(Alignment.End)
            ) {
                Text(text = "Register")
            }
        }
    }
}