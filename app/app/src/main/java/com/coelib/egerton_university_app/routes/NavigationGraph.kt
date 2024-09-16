package com.coelib.egerton_university_app.routes

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.coelib.egerton_university_app.components.bottombar.BottomNavigationItems
import com.coelib.egerton_university_app.screens.home.EcampusScreen
import com.coelib.egerton_university_app.screens.home.FirstStepScreen
import com.coelib.egerton_university_app.screens.home.MedicalHelp
import com.coelib.egerton_university_app.screens.home.PortalScreen
import com.coelib.egerton_university_app.screens.home.HomeScreen
import com.coelib.egerton_university_app.screens.info.InfoScreen
import com.coelib.egerton_university_app.screens.location.LocationScreen
import com.coelib.egerton_university_app.screens.news.NewsScreen

@Composable
fun NavigationGraph(navController: NavHostController, onBottomBarVisibilityChanged: (Boolean) -> Unit){
    NavHost(navController, startDestination = Routes.Home.routes ){

        composable(Routes.Home.routes){
            onBottomBarVisibilityChanged(false)
           // Splash(navController = navController)
        }
         composable(Routes.FirstStep.routes){
             onBottomBarVisibilityChanged(false)
             FirstStepScreen()
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
        composable(BottomNavigationItems.HomeScreen.route) {
            onBottomBarVisibilityChanged(true)
            HomeScreen()
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