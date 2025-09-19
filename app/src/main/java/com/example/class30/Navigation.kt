package com.example.class30

sealed class Screen(val route: String) {
    object Login : Screen("LoginScreen")
    object Register : Screen("RegisterUser")
    object Menu : Screen("MenuStart")
}