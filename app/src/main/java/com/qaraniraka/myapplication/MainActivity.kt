package com.qaraniraka.myapplication

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
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
        val userSession: String? = intent.getStringExtra("user_session")
        val userSessionPreferencesRepository =
            (application as GHGEmApplication).userSessionPreferencesRepository

        // Might cause problem if this the Flow takes too long. But this should be sufficient
        // for this specific case.
        var currentUserSession: String
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

@Composable
fun NavButton(
    icon: Painter,
    isActive: Boolean = false,
    onClick: () -> Unit = {}
) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .size(80.dp)
            .clickable { onClick() }
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .width(64.dp)
                .height(32.dp)
                .clip(CircleShape)
                .background(
                    if (isActive)
                        MaterialTheme.colorScheme.secondaryContainer
                    else Color.Transparent
                )
        ) {
            Icon(
                painter = icon,
                contentDescription = ""
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GHGEmmissionTopAppBar() {
    TopAppBar(title = { Text(text = "GHG Emmission") })
}

@Composable
fun GHGEmmissionBottomAppBar(
    currentRoute: String = "",
    onHomeIconClicked: () -> Unit = {},
    onStatIconClicked: () -> Unit = {},
    onProfileIconClicked: () -> Unit = {}
) {
    Row(
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
    ) {
        NavButton(
            icon = painterResource(id = R.drawable.outline_home_24),
            isActive = currentRoute == Routes.MainScreen.name,
            onClick = onHomeIconClicked
        )
        NavButton(
            icon = painterResource(id = R.drawable.baseline_bar_chart_24),
            isActive = currentRoute == Routes.StatScreen.name,
            onClick = onStatIconClicked
        )
        NavButton(
            icon = painterResource(id = R.drawable.person_24),
            isActive = currentRoute == Routes.ProfileScreen.name,
            onClick = onProfileIconClicked
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun GHGEmmssionApp(navController: NavHostController = rememberNavController()) {
    var currentRoute by remember { mutableStateOf(Routes.MainScreen.name) }

    GHGEmissionTheme {
        Scaffold(
            topBar = { GHGEmmissionTopAppBar() },
            bottomBar = {
                GHGEmmissionBottomAppBar(
                    currentRoute = currentRoute,
                    onHomeIconClicked = {
                        currentRoute = Routes.MainScreen.name
                        navController.navigate(Routes.MainScreen.name)
                    },
                    onStatIconClicked = {
                        currentRoute = Routes.StatScreen.name
                        navController.navigate(Routes.StatScreen.name)
                    },
                    onProfileIconClicked = {
                        currentRoute = Routes.ProfileScreen.name
                        navController.navigate(Routes.ProfileScreen.name)
                    }
                )
            },
            modifier = Modifier.fillMaxSize()
        ) { paddingValues ->
            NavHost(
                navController = navController,
                startDestination = Routes.MainScreen.name,
                modifier = Modifier.padding(paddingValues)
            ) {
                composable(route = Routes.MainScreen.name) {
                    MainScreen()
                }
                composable(route = Routes.StatScreen.name) {

                }
                composable(route = Routes.ProfileScreen.name) {

                }
            }
        }
    }
}