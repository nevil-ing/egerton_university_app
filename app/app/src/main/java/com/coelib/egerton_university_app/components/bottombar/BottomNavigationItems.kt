package com.coelib.egerton_university_app.components.bottombar

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Place
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import com.coelib.egerton_university_app.R

sealed class BottomNavigationItems(
    val route: String,
    val title: String? = null,
    val icon: IconType
) {
    data object HomeScreen : BottomNavigationItems(
        route = "HomeScreen",
        title = "Home",
        icon = IconType.VectorIcon(Icons.Filled.Home)
    )
    data object NewsScreen : BottomNavigationItems(
        route = "NewsScreen",
        title = "News",
        icon = IconType.DrawableIcon(R.drawable.news)
    )
    data object LocationScreen : BottomNavigationItems(
        route = "LocationScreen",
        title = "Location",
        icon = IconType.VectorIcon(Icons.Filled.Place)
    )
    data object InfoScreen : BottomNavigationItems(
        route = "InfoScreen",
        title = "Info",
        icon = IconType.VectorIcon(Icons.Filled.Info)
    )
}

sealed class IconType {
    data class VectorIcon(val imageVector: ImageVector) : IconType()
    data class DrawableIcon(val resourceId: Int) : IconType()
}
