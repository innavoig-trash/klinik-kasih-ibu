package com.example.klinikkasihibu.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.klinikkasihibu.ui.route.leave.LeaveRoute
import com.example.klinikkasihibu.ui.route.login.LoginRoute
import com.example.klinikkasihibu.ui.route.main.MainRoute
import com.example.klinikkasihibu.ui.route.signup.SignUpRoute
import com.example.klinikkasihibu.ui.route.splash.SplashRoute

@Composable
fun RootNavGraph(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route
    ) {
        composable(route = Screen.Splash.route) {
            SplashRoute(
                toMain = {
                    navController.navigate(Screen.Main.route) {
                        popUpTo(Screen.Splash.route) {
                            inclusive = true
                        }
                    }
                },
                toLogin = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.Splash.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }
        composable(route = Screen.SignUp.route) {
            SignUpRoute(
                onBackStack = {
                    navController.popBackStack()
                },
                toMain = {
                    navController.navigate(Screen.Main.route) {
                        popUpTo(Screen.SignUp.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }
        composable(route = Screen.Login.route) {
            LoginRoute(
                toSignUp = {
                    navController.navigate(Screen.SignUp.route) {
                        popUpTo(Screen.Login.route) {
                            inclusive = true
                        }
                    }
                },
                toMain = {
                    navController.navigate(Screen.Main.route) {
                        popUpTo(Screen.Login.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }
        composable(route = Screen.Main.route) {
            MainRoute(
                toLeave = {
                    navController.navigate(Screen.Leave.route)
                },
                toLogin = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.Main.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }
        composable(route = Screen.Leave.route) {
            LeaveRoute(
                onBackStack = navController::navigateUp,
            )
        }
    }
}