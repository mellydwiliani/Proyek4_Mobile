package com.example.hanyarunrun

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import androidx.navigation.compose.rememberNavController
import com.example.hanyarunrun.ui.AppNavHost
import com.example.hanyarunrun.ui.theme.HanyarunrunTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HanyarunrunTheme {
                val navController = rememberNavController()
                AppNavHost(navController = navController)
            }
        }
    }
}