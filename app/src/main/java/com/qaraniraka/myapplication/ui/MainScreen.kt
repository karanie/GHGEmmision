package com.qaraniraka.myapplication.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun MainScreen(modifier: Modifier = Modifier) {
    Surface(modifier = modifier) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(
                text = "Rekam Aktivitas",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            Row(
                horizontalArrangement = Arrangement.SpaceAround,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                ElevatedCard(
                    onClick = { /* TODO */ },
                    modifier = Modifier
                        .height(120.dp)
                        .width(160.dp)
                ) {
                    Column(
                        verticalArrangement = Arrangement.Bottom, modifier = Modifier
                            .padding(16.dp)
                            .fillMaxSize()
                    ) {
                        Text(text = "Aktivitas Makan")
                    }
                }
                ElevatedCard(
                    onClick = { /* TODO */ },
                    modifier = Modifier
                        .height(120.dp)
                        .width(160.dp)
                ) {
                    Column(
                        verticalArrangement = Arrangement.Bottom, modifier = Modifier
                            .padding(16.dp)
                            .fillMaxSize()
                    ) {
                        Text(text = "Aktivitas Berkendara")
                    }
                }
            }
        }
    }
}