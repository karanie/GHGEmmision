package com.qaraniraka.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
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
fun WelcomeActivityApp() {
    GHGEmissionTheme {
        WelcomeScreen()
    }
}