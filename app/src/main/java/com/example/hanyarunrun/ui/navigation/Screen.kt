package com.example.hanyarunrun.ui.navigation

object Screen {
    val Splash = ScreenRoute("splash")
    val Home = ScreenRoute("home")
    val AddData = ScreenRoute("add_data")
    val List = ScreenRoute("list")
    val Profile = ScreenRoute("profile")

    data class ScreenRoute(val route: String)
}