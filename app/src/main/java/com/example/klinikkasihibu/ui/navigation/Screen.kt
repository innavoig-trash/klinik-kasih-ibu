package com.example.klinikkasihibu.ui.navigation

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Main: Screen("main")
    object Home : Screen("home")
    object Notification: Screen("notification")
    object Profile : Screen("profile")
    object EditProfile: Screen("edit_profile")
    object Login : Screen("login")
    object SignUp: Screen("signup")
    object Leave: Screen("leave")
    object Payroll: Screen("payroll")
}