package com.example.klinikkasihibu.ui.navigation

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Main: Screen("main")
    object Home : Screen("home")
    object Employee : Screen("employee")
    object Notification: Screen("notification")
    object Profile : Screen("profile")
    object Login : Screen("login")
    object SignUp: Screen("signup")
    object Leave: Screen("leave")
}