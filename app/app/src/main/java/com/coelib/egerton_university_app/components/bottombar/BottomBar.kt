package com.coelib.egerton_university_app.components.bottombar

import androidx.compose.foundation.Image
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun BottomBar(
    navController: NavHostController,
    state: Boolean,
    modifier: Modifier = Modifier
){
    val screens = listOf(
        BottomNavigationItems.HomeScreen,
        BottomNavigationItems.NewsScreen,
        BottomNavigationItems.LocationScreen,
        BottomNavigationItems.InfoScreen
    )

    NavigationBar(
        modifier = modifier,
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        screens.forEach { screen ->
            NavigationBarItem(
                selected = currentRoute == screen.route,
                label = {
                    Text(text = screen.title!!)
                },
                onClick = {
                    navController.navigate(screen.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = {
                    when (val icon = screen.icon) {
                        is IconType.VectorIcon -> {
                            Icon(imageVector = icon.imageVector, contentDescription = screen.title)
                        }
                        is IconType.DrawableIcon -> {
                             // Change color based on theme

                            Image(
                                painter = painterResource(id = icon.resourceId),
                                contentDescription = screen.title,
                               // Apply color filter
                            )
                        }
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    // Customize colors as needed
                )
            )
        }
    }
}
