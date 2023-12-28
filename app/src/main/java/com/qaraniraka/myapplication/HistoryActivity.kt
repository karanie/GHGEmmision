package com.qaraniraka.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.qaraniraka.myapplication.ui.RiwayatScreen
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
fun HistoryActivityApp() {
    GHGEmissionTheme {
        RiwayatScreen()
    }
}