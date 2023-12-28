package com.qaraniraka.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.qaraniraka.myapplication.ui.BerkendaraScreen
import com.qaraniraka.myapplication.ui.EmissionResultScreen
import com.qaraniraka.myapplication.ui.MakanScreen
import com.qaraniraka.myapplication.ui.Routes
import com.qaraniraka.myapplication.ui.theme.GHGEmissionTheme

class AddActivityActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val activityType = intent.getStringExtra("activity_type") ?: Routes.BerkendaraScreen.name
        setContent {
            AddActivityActivityApp(activityType = activityType)
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AddActivityActivityApp(
    navController: NavHostController = rememberNavController(),
    activityType: String = Routes.BerkendaraScreen.name
) {
    GHGEmissionTheme {
        NavHost(navController = navController, startDestination = activityType) {
            composable(route = Routes.BerkendaraScreen.name) {
                BerkendaraScreen()
            }
            composable(route = Routes.MakanScreen.name) {
                MakanScreen()
            }
            composable(route = Routes.EmissionResultScreen.name) {
                EmissionResultScreen()
            }
        }
    }
}