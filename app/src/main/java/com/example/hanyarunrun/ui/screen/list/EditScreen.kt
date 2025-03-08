package com.example.hanyarunrun.ui.screen.list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.hanyarunrun.ui.theme.HanyarunrunTheme
import com.example.hanyarunrun.viewmodel.DataViewModel

@Composable
fun EditScreen(
    navController: NavHostController,
    viewModel: DataViewModel,
    dataId: Int
) {
    HanyarunrunTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = HanyarunrunTheme.colors.uiBackground
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Detail Data (Kosong)",
                    style = MaterialTheme.typography.headlineMedium.copy(fontSize = 28.sp),
                    color = HanyarunrunTheme.colors.textPrimary
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = { navController.popBackStack() },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = HanyarunrunTheme.colors.brand,
                        contentColor = HanyarunrunTheme.colors.textPrimary
                    )
                ) {
                    Text("Kembali")
                }
            }
        }
    }
}