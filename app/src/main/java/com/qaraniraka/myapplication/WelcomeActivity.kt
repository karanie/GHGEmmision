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
import com.qaraniraka.myapplication.ui.LoginScreen
import com.qaraniraka.myapplication.ui.RegisterScreen
import com.qaraniraka.myapplication.ui.Routes
import com.qaraniraka.myapplication.ui.WelcomeScreen
import com.qaraniraka.myapplication.ui.theme.GHGEmissionTheme

class WelcomeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WelcomeActivityApp()
        }
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun WelcomeActivityApp(navController: NavHostController = rememberNavController()) {
    GHGEmissionTheme {
        NavHost(
            navController = navController, startDestination = Routes.WelcomeScreen.name
        ) {
            composable(route = Routes.WelcomeScreen.name) {
                WelcomeScreen(
                    onRegisterButtonClicked = { navController.navigate(Routes.RegisterScreen.name) },
                    onLoginButtonClicked = { navController.navigate(Routes.LoginScreen.name) }
                )
            }
            composable(route = Routes.LoginScreen.name) {
                LoginScreen()
            }
            composable(route = Routes.RegisterScreen.name) {
                RegisterScreen()
            }
        }
    }
}