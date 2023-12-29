package com.qaraniraka.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.qaraniraka.myapplication.ui.StatisticScreen
import com.qaraniraka.myapplication.ui.theme.GHGEmissionTheme

class StatisticActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StatisticActivityApp()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun StatisticActivityApp() {
    GHGEmissionTheme {
        StatisticScreen()
    }
}