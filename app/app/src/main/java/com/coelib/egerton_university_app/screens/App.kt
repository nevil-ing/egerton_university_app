package com.coelib.egerton_university_app.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.coelib.egerton_university_app.components.bottombar.BottomBar
import com.coelib.egerton_university_app.routes.NavigationGraph

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun App(isDarkTheme: Boolean, onThemeChange: (Boolean) -> Unit) {
    val navController: NavHostController = rememberNavController()
    var buttonsVisible by remember { mutableStateOf(true) }

        Scaffold(
            bottomBar = {
                if (buttonsVisible) {
                    BottomBar(navController = navController, state = buttonsVisible)
                }
            }
        ) { paddingValues ->
            Box(
                modifier = Modifier.padding(paddingValues)
            ) {
                NavigationGraph(
                navController = navController,
                    isDarkTheme = isDarkTheme, // Pass the theme state
                    onThemeChange = onThemeChange, ) { isVisible ->
                    buttonsVisible = isVisible
                }
            }
        }
    }
