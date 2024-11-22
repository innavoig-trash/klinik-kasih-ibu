package com.example.klinikkasihibu.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.People
import androidx.compose.material.icons.outlined.Person
import androidx.compose.ui.graphics.vector.ImageVector

data class BottomNavigationItem(
    val label: String,
    val icon: ImageVector,
    val unselectedIcon: ImageVector,
    val route: String
) {
    companion object {
        val items = listOf(
            BottomNavigationItem(
                label = "Home",
                icon = Icons.Filled.Home,
                unselectedIcon = Icons.Outlined.Home,
                route = Screen.Home.route
            ),
            BottomNavigationItem(
                label = "Notification",
                icon = Icons.Filled.Notifications,
                unselectedIcon = Icons.Outlined.Notifications,
                route = Screen.Notification.route
            ),
            BottomNavigationItem(
                label = "Profile",
                icon = Icons.Filled.Person,
                unselectedIcon = Icons.Outlined.Person,
                route = Screen.Profile.route
            )
        )
    }
}