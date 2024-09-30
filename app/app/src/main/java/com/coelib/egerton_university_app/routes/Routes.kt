package com.coelib.egerton_university_app.routes

sealed class Routes(val routes: String) {
    //object Splash : Routes("SplashScreen")
    data object Home : Routes("HomeScreen")
    data object News : Routes("NewsScreen")
    data object Info : Routes("InfoScreen")
    data object Location : Routes("LocationScreen")
    data object FirstStep : Routes("FirstStepsScreen")
    data object MedicalHelp: Routes("MedicalHelp")
    data object Ecampus: Routes("EcampusScreen")
    data object Portal: Routes("PortalScreen")
    data object Office: Routes("OfficeScreen")
    data object Lecture: Routes("LectureHallScreen")
    data object  Cafeteria: Routes("CafeteriaScreen")
    data object Hostel: Routes("HostelScreen")
    data object Settings: Routes("SettingsScreen")
}