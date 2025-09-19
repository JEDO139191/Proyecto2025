package com.example.class30

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.class30.ui.login.LoginScreen
import com.example.class30.ui.register.RegisterUser

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.Login.route) {
        composable(Screen.Login.route) {
            LoginScreen(
                onLoginSuccess = { navController.navigate(Screen.Menu.route) },
                onRegisterClick = { navController.navigate(Screen.Register.route) }
            )
        }
        composable(Screen.Register.route) {
            RegisterUser(
                onRegisterSuccess = { navController.navigate(Screen.Menu.route) },
                onBackClick = { navController.popBackStack() }
            )

        }

        composable(Screen.Menu.route) {
            MenuStart(

            )
        }
    }
}