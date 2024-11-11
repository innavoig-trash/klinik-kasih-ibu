package com.example.klinikkasihibu.ui.route.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.klinikkasihibu.ui.navigation.BottomNavigationItem
import com.example.klinikkasihibu.ui.navigation.MainNavGraph

@Composable
fun MainRoute(
    toLeave: () -> Unit,
    toLogin: () -> Unit,
    navController: NavHostController = rememberNavController()
) {
    Box(
        modifier = Modifier,
        contentAlignment = Alignment.BottomCenter
    ) {
        MainNavGraph(
            toLeave = toLeave,
            toLogin = toLogin,
            navController = navController,
        )
        NavigationBar(
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.tertiary,
            tonalElevation = 0.dp
        ) {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentDestination = navBackStackEntry?.destination
            BottomNavigationItem.items.forEach { item ->
                NavigationBarItem(
                    colors = NavigationBarItemColors(
                        selectedIndicatorColor = Color.Transparent,
                        selectedTextColor = MaterialTheme.colorScheme.tertiary,
                        selectedIconColor = MaterialTheme.colorScheme.tertiary,
                        unselectedIconColor = Color.Black,
                        unselectedTextColor = Color.Black,
                        disabledIconColor = Color.Black,
                        disabledTextColor = Color.Black
                    ),
                    icon = {
                        Icon(
                            when (currentDestination?.hierarchy?.any { it.route == item.route } == true) {
                                true -> item.icon
                                false -> item.unselectedIcon
                            },
                            contentDescription = null
                        )
                    },
                    label = { Text(text = item.label) },
                    selected = currentDestination?.hierarchy?.any { it.route == item.route } == true,
                    onClick = {
                        navController.navigate(item.route) {
                            // Pop up to the start destination of the graph to
                            // avoid building up a large stack of destinations
                            // on the back stack as users select items
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            // Avoid multiple copies of the same destination when
                            // reselecting the same item
                            launchSingleTop = true
                            // Restore state when reselecting a previously selected item
                            restoreState = true
                        }
                    }
                )
            }
        }
    }
}