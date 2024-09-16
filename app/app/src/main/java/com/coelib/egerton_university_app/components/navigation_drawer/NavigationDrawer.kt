package com.coelib.egerton_university_app.components.navigation_drawer

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.coelib.egerton_university_app.components.ThemeSwitcher

@Composable
fun NavigationDrawer(){
  val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    var isDarkTheme by remember { mutableStateOf(false) }
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = { /*TODO*/
            ModalDrawerSheet {
                Spacer(modifier = Modifier.height(16.dp))
                ThemeSwitcher(isDarkTheme) { isDarkTheme = it }
            }
        }
    ) {

    }

}