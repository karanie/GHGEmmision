package com.qaraniraka.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.qaraniraka.myapplication.model.ActivityHistoryData
import com.qaraniraka.myapplication.ui.HistoryDetailScreen
import com.qaraniraka.myapplication.ui.RiwayatScreen
import com.qaraniraka.myapplication.ui.Routes
import com.qaraniraka.myapplication.ui.theme.GHGEmissionTheme

class HistoryActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HistoryActivityApp()
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HistoryActivityApp(navController: NavHostController = rememberNavController()) {
    var selectedHistoryData by remember { mutableStateOf<ActivityHistoryData?>(null) }
    GHGEmissionTheme {
        NavHost(
            navController = navController,
            startDestination = Routes.HistoryScreen.name
        ) {
            composable(route = Routes.HistoryScreen.name) {
                RiwayatScreen(
                    onHistoryItemClick = {
                        selectedHistoryData = it
                        navController.navigate(Routes.HistoryDetailScreen.name)
                    }
                )
            }
            composable(route = Routes.HistoryDetailScreen.name) {
                HistoryDetailScreen(selectedHistoryData)
            }
        }
    }
}