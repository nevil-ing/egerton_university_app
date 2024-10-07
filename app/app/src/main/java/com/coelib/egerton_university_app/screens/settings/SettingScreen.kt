package com.coelib.egerton_university_app.screens.settings

import androidx.compose.animation.core.VisibilityThreshold
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.coelib.egerton_university_app.components.ThemeSwitcher

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingScreen(isDarkTheme: Boolean, onThemeChange: (Boolean) -> Unit,navigateBack: () -> Unit) {
    Scaffold(
        topBar = {

            TopAppBar(
                title = { Text(
                    text = "Settings",
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.titleLarge
                ) },
                navigationIcon = {
                    IconButton(onClick = navigateBack) {
                        Icon(
                            imageVector = Icons.Filled.KeyboardArrowLeft,
                            contentDescription = "Back"
                        )
                    }
                }
            )
                 },
        content = { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                Column {
                    Spacer(modifier = Modifier.height(50.dp))
                    SettingsBody(isDarkTheme, onThemeChange)
                }
            }
        }
    )
}


@Composable
fun SettingsBody(isDarkTheme: Boolean, onThemeChange: (Boolean) -> Unit) {
    Column {
                ListItem(
                    modifier = Modifier
                        .padding(8.dp),
                    shadowElevation = Dp.Companion.VisibilityThreshold ,
                    headlineContent = {},
                    leadingContent = {
                        Text(text = "DarkMode")
                    },
                    trailingContent = {
                        ThemeSwitcher(isDarkTheme = isDarkTheme, onThemeChange = onThemeChange)
                    }
                )
    }
}
