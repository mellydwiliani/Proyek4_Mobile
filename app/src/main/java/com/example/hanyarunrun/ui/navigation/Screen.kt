package com.example.hanyarunrun.ui.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object List : Screen("list")
    //object Profile : Screen("profile")
}