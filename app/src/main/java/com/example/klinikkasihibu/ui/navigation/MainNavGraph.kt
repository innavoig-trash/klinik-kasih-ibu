package com.example.klinikkasihibu.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.klinikkasihibu.ui.route.main.home.HomeRoute
import com.example.klinikkasihibu.ui.route.main.notif.NotificationRoute
import com.example.klinikkasihibu.ui.route.main.profile.ProfileRoute

@Composable
fun MainNavGraph(
    toLeave: () -> Unit,
    toPayroll: () -> Unit,
    toLogin: () -> Unit,
    navController: NavHostController = rememberNavController(),
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(route = Screen.Home.route) {
            HomeRoute(
                toNotification = {
                    navController.navigate(Screen.Notification.route)
                },
                toPayroll = toPayroll,
                toLeave = toLeave
            )
        }
        composable(route = Screen.Profile.route) {
            ProfileRoute(
                toLogin = toLogin
            )
        }
        composable(route = Screen.Notification.route) {
            NotificationRoute()
        }
    }
}