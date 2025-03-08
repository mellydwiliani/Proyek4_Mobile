package com.example.hanyarunrun.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.hanyarunrun.ui.navigation.Screen
import com.example.hanyarunrun.ui.screen.adddata.DataEntryScreen
import com.example.hanyarunrun.ui.screen.home.HomeScreen
import com.example.hanyarunrun.ui.screen.list.DataListScreen
import com.example.hanyarunrun.ui.screen.list.EditScreen
import com.example.hanyarunrun.ui.screen.profile.ProfileScreen
import com.example.hanyarunrun.ui.screen.splash.SplashScreen
import com.example.hanyarunrun.viewmodel.DataViewModel
import com.example.hanyarunrun.viewmodel.ProfileViewModel
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun AppNavHost(
    navController: NavHostController,
    startDestination: String = Screen.Splash.route // Ubah ke Splash sebagai start destination
) {
    val dataViewModel: DataViewModel = hiltViewModel()
    val profileViewModel: ProfileViewModel = viewModel()

    Scaffold(
        bottomBar = { BottomBar(navController = navController) }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = startDestination,
            modifier = Modifier.padding(paddingValues)
        ) {
            composable(Screen.Splash.route) {
                SplashScreen(navController = navController)
            }
            composable(Screen.Home.route) {
                HomeScreen(viewModel = dataViewModel, navController = navController)
            }
            composable(Screen.AddData.route) {
                DataEntryScreen(navController = navController, viewModel = dataViewModel)
            }
            composable(Screen.List.route) {
                DataListScreen(navController = navController, viewModel = dataViewModel)
            }
            composable(Screen.Profile.route) {
                ProfileScreen(navController = navController, viewModel = profileViewModel)
            }
            composable("edit/{dataId}") { backStackEntry ->
                val dataId = backStackEntry.arguments?.getString("dataId")?.toIntOrNull() ?: 0
                EditScreen(navController = navController, viewModel = dataViewModel, dataId = dataId)
            }
        }
    }
}