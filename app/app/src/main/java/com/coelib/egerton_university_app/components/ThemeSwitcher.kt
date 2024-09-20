package com.coelib.egerton_university_app.components

import androidx.compose.material3.Switch
import androidx.compose.runtime.Composable

@Composable
fun ThemeSwitcher(isDarkTheme: Boolean, onThemeChange: (Boolean) -> Unit) {
    Switch(
        checked = isDarkTheme,
        onCheckedChange = { onThemeChange(it) }
    )
}
