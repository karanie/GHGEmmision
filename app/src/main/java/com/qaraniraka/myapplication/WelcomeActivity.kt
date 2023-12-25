package com.qaraniraka.myapplication

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

class WelcomeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val userSessionPreferencesRepository = (application as GHGEmApplication).userSessionPreferencesRepository
        val currentUserSession : String
        runBlocking(Dispatchers.IO) {
            currentUserSession = userSessionPreferencesRepository.userSession.first()
        }
        if (currentUserSession != "") {
            val intent = Intent(this, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            this.startActivity(intent)
        }

        setContent {
            WelcomeActivityApp()
        }
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun WelcomeActivityApp(navController: NavHostController = rememberNavController()) {
    val context = LocalContext.current

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
                LoginScreen(onLoginButtonClick = {
                    val intent = Intent(context, MainActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                    intent.putExtra("user_session", "foobar")
                    context.startActivity(intent)
                })
            }
            composable(route = Routes.RegisterScreen.name) {
                RegisterScreen()
            }
        }
    }
}