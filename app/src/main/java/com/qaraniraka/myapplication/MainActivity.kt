package com.qaraniraka.myapplication

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.qaraniraka.myapplication.ui.MainScreen
import com.qaraniraka.myapplication.ui.Routes
import com.qaraniraka.myapplication.ui.theme.GHGEmissionTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var userSession : String? = intent.getStringExtra("user_session")
        val userSessionPreferencesRepository = (application as GHGEmApplication).userSessionPreferencesRepository

        // Might cause problem if this the Flow takes too long. But this should be sufficient
        // for this specific case.
        var currentUserSession : String
        runBlocking(Dispatchers.IO) {
            currentUserSession = userSessionPreferencesRepository.userSession.first()
        }
        if (!userSession.isNullOrEmpty()) {
            runBlocking(Dispatchers.IO) {
                userSessionPreferencesRepository.saveUserSession(userSession)
            }
        }
        runBlocking(Dispatchers.IO) {
            currentUserSession = userSessionPreferencesRepository.userSession.first()
        }
        if (currentUserSession == "") {
            val intent = Intent(this, WelcomeActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            this.startActivity(intent)
        }

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
@Composable
fun GHGEmmssionApp() {
    GHGEmissionTheme {
        Scaffold(
            topBar = { GHGEmmissionTopAppBar() },
            bottomBar = { GHGEmmissionBottomAppBar() },
            modifier = Modifier.fillMaxSize()
        ) { paddingValues ->
            NavHost(
                navController = rememberNavController(),
                startDestination = Routes.MainScreen.name,
                modifier = Modifier.padding(paddingValues)
            ) {
                composable(route = Routes.MainScreen.name) {
                    MainScreen()
                }
            }
        }
    }
}