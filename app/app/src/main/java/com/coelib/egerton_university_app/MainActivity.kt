package com.coelib.egerton_university_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.coelib.egerton_university_app.screens.App
import com.coelib.egerton_university_app.ui.theme.Egerton_university_appTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        // Top-level dark theme state
        var isDarkTheme by mutableStateOf(false)

        setContent {

            Egerton_university_appTheme(darkTheme = isDarkTheme) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // Pass down the theme state and its toggle function to the app
                    App(isDarkTheme = isDarkTheme, onThemeChange = { isDarkTheme = it })
                }
            }
        }
    }
}
