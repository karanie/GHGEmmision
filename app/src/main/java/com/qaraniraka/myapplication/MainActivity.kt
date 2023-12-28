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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.qaraniraka.myapplication.model.UserLogoutPostData
import com.qaraniraka.myapplication.ui.MainScreen
import com.qaraniraka.myapplication.ui.ProfileScreen
import com.qaraniraka.myapplication.ui.Routes
import com.qaraniraka.myapplication.ui.theme.GHGEmissionTheme
import com.qaraniraka.myapplication.viewmodel.UserSessionViewModel
import com.qaraniraka.myapplication.viewmodel.UserUiState
import com.qaraniraka.myapplication.viewmodel.UserViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import java.util.Calendar

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val userSessionPreferencesRepository =
            (application as GHGEmApplication).userSessionPreferencesRepository

        // Might cause problem if this the Flow takes too long. But this should be sufficient
        // for this specific case.
        var currentUserSession: String
        runBlocking(Dispatchers.IO) {
            currentUserSession = userSessionPreferencesRepository.userSession.first()
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
fun GHGEmmissionTopAppBar(title: String = "GHG Emission") {
    TopAppBar(title = { Text(text = title) })
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
fun GHGEmmssionApp(
    userSessionViewModel: UserSessionViewModel = viewModel(
        factory = UserSessionViewModel.Factory
    ),
    navController: NavHostController = rememberNavController()
) {
    val userViewModelForLogout: UserViewModel = viewModel()
    val userSession = userSessionViewModel.uiState.collectAsState().value.userSession
    val userViewModelForUserData: UserViewModel = viewModel()

    val context = LocalContext.current
    var currentRoute by remember { mutableStateOf(Routes.MainScreen.name) }
    val timeOfDay = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
    val userName = if (userViewModelForUserData.userUiState is UserUiState.UserDataSuccess)
        (userViewModelForUserData.userUiState as UserUiState.UserDataSuccess).data.fullName
    else ""
    val greeting = if (timeOfDay in 0..11) "Selamat Pagi, $userName"
    else if (timeOfDay in 12..13) "Selamat Siang, $userName"
    else if (timeOfDay in 14..18) "Selamat Sore, $userName"
    else "Selamat Malam, $userName"
    GHGEmissionTheme {
        Scaffold(
            topBar = {
                GHGEmmissionTopAppBar(
                    if (currentRoute == Routes.ProfileScreen.name)
                        greeting
                    else "GHG Emission"
                )
            },
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
                        if (userSession.isNotEmpty()) {
                            userViewModelForUserData.getUserDataBySession(userSession)
                        }
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
                    MainScreen(
                        onActivityBerkendaraClick = {
                            val intent = Intent(context, AddActivityActivity::class.java)
                            intent.putExtra("activity_type", Routes.BerkendaraScreen.name)
                            context.startActivity(intent)
                        },
                        onActivityMakanClick = {
                            val intent = Intent(context, AddActivityActivity::class.java)
                            intent.putExtra("activity_type", Routes.MakanScreen.name)
                            context.startActivity(intent)
                        }
                    )
                }
                composable(route = Routes.StatScreen.name) {

                }
                composable(route = Routes.ProfileScreen.name) {
                    ProfileScreen(
                        onLogoutClick = {
                            userViewModelForLogout.logoutUser(
                                UserLogoutPostData(userSession)
                            )
                            userSessionViewModel.clearUserSession()
                            val intent = Intent(context, WelcomeActivity::class.java)
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                            context.startActivity(intent)
                        }
                    )
                }
            }
        }
    }
}