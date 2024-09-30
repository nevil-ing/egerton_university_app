package com.coelib.egerton_university_app.routes

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.coelib.egerton_university_app.components.bottombar.BottomNavigationItems
import com.coelib.egerton_university_app.screens.home.pages.EcampusScreen
import com.coelib.egerton_university_app.screens.home.pages.FirstStepsScreen
import com.coelib.egerton_university_app.screens.home.pages.MedicalHelp
import com.coelib.egerton_university_app.screens.home.pages.PortalScreen
import com.coelib.egerton_university_app.screens.home.HomeScreen
import com.coelib.egerton_university_app.screens.info.InfoScreen
import com.coelib.egerton_university_app.screens.location.LocationScreen
import com.coelib.egerton_university_app.screens.location.pages.CafeteriaScreen
import com.coelib.egerton_university_app.screens.location.pages.HostelScreen
import com.coelib.egerton_university_app.screens.location.pages.LectureHallScreen
import com.coelib.egerton_university_app.screens.location.pages.OfficeScreen
import com.coelib.egerton_university_app.screens.news.NewsScreen
import com.coelib.egerton_university_app.screens.settings.SettingScreen
import com.google.androidgamesdk.gametextinput.Settings

@Composable
fun NavigationGraph(
    navController: NavHostController,
    isDarkTheme: Boolean, // Pass the dark theme state
    onThemeChange: (Boolean) -> Unit,
    onBottomBarVisibilityChanged: (Boolean) -> Unit){
    NavHost(navController, startDestination = Routes.Home.routes ){

        composable(Routes.Home.routes){
            onBottomBarVisibilityChanged(false)
           // Splash(navController = navController)
        }
         composable(Routes.FirstStep.routes){
             onBottomBarVisibilityChanged(false)
             FirstStepsScreen()
         }
        composable(Routes.MedicalHelp.routes){
            onBottomBarVisibilityChanged(false)
            MedicalHelp()
        }
        composable(Routes.Ecampus.routes){
            onBottomBarVisibilityChanged(false)
            EcampusScreen()
        }
        composable(Routes.Portal.routes){
            onBottomBarVisibilityChanged(false)
            PortalScreen()
        }
        composable(Routes.Office.routes) {
            onBottomBarVisibilityChanged(false)
            OfficeScreen()
        }
        composable(Routes.Lecture.routes) {
            onBottomBarVisibilityChanged(false)
            LectureHallScreen()
        }
        composable(Routes.Cafeteria.routes) {
            onBottomBarVisibilityChanged(false)
            CafeteriaScreen()
        }
        composable(Routes.Hostel.routes) {
            onBottomBarVisibilityChanged(false)
            HostelScreen()
        }
        composable(Routes.Settings.routes){
            onBottomBarVisibilityChanged(false)
            SettingScreen(isDarkTheme = isDarkTheme, onThemeChange = onThemeChange)
        }
        composable(BottomNavigationItems.HomeScreen.route) {
            onBottomBarVisibilityChanged(true)
            HomeScreen(navController)
        }
        composable(BottomNavigationItems.NewsScreen.route) {
            onBottomBarVisibilityChanged(true)
            NewsScreen()
        }
        composable(BottomNavigationItems.InfoScreen.route) {
            onBottomBarVisibilityChanged(true)
            InfoScreen()
        }
        composable(BottomNavigationItems.LocationScreen.route) {
            onBottomBarVisibilityChanged(true)
            LocationScreen()
        }

    }
}