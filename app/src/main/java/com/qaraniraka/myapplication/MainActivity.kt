package com.qaraniraka.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.qaraniraka.myapplication.ui.theme.GHGEmissionTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GHGEmmssionApp()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GHGEmmissionTopAppBar() {
    TopAppBar(title = { Text(text = "GHG Emmission") })
}

@Composable
fun GHGEmmissionBottomAppBar() {
    Row(
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
    ) {
        Icon(
            painter = painterResource(id = R.drawable.outline_home_24),
            contentDescription = ""
        )
        Icon(
            painter = painterResource(id = R.drawable.baseline_bar_chart_24),
            contentDescription = ""
        )
        Icon(
            painter = painterResource(id = R.drawable.person_24),
            contentDescription = ""
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GHGEmmssionApp() {
    GHGEmissionTheme {
        Scaffold(
            topBar = { GHGEmmissionTopAppBar() },
            bottomBar = { GHGEmmissionBottomAppBar() },
            modifier = Modifier.fillMaxSize()
        ) { paddingValues ->
            Surface(modifier = Modifier.padding(paddingValues)) {
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
    }
}