package com.example.hanyarunrun.ui.screen.list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavHostController
import androidx.compose.runtime.livedata.observeAsState
import com.example.hanyarunrun.viewmodel.DataViewModel
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Settings


@Composable
fun DataListScreen(navController: NavHostController, viewModel: DataViewModel) {
    val dataList by viewModel.dataList.observeAsState(emptyList())

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate("add") // Navigasi ke halaman entry data
                },
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Text("+", style = MaterialTheme.typography.headlineSmall, color = MaterialTheme.colorScheme.onPrimary)
            }
        },
        bottomBar = {
            BottomNavigationBar(navController = navController)
        },
        content = { paddingValues ->
            if (dataList.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "No Data Available",
                            style = MaterialTheme.typography.headlineMedium
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(
                            onClick = {
                                navController.navigate("add") // Navigasi ke halaman entry data
                            },
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text(text = "Add Data")
                        }
                    }
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    contentPadding = paddingValues
                ) {
                    items(dataList) { item ->
                        Card(
                            shape = RoundedCornerShape(12.dp),
                            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(
                                modifier = Modifier
                                    .background(MaterialTheme.colorScheme.surface)
                                    .padding(16.dp)
                            ) {
                                Text(
                                    text = "Provinsi: ${item.namaProvinsi} (${item.kodeProvinsi})",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = "Kabupaten/Kota: ${item.namaKabupatenKota} (${item.kodeKabupatenKota})",
                                    style = MaterialTheme.typography.bodyMedium
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = "Total: ${item.total} ${item.satuan}",
                                    style = MaterialTheme.typography.bodyMedium
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = "Tahun: ${item.tahun}",
                                    style = MaterialTheme.typography.bodyMedium
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.End,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Button(
                                        onClick = {
                                            navController.navigate("edit/${item.id}")
                                        },
                                        shape = RoundedCornerShape(8.dp)
                                    ) {
                                        Text(text = "Edit")
                                    }

                                    Button(
                                        onClick = {
                                            viewModel.deleteData(item.id) // Memanggil fungsi delete di ViewModel
                                        },
                                        colors = ButtonDefaults.buttonColors(
                                            containerColor = MaterialTheme.colorScheme.error
                                        ),
                                        shape = RoundedCornerShape(8.dp)
                                    ) {
                                        Text(text = "Delete", color = MaterialTheme.colorScheme.onError)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    )
}

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    NavigationBar {
        NavigationBarItem(
            selected = false, // Ganti dengan logika untuk menandai halaman aktif
            onClick = { navController.navigate("form") }, // Navigasi ke halaman home
            label = { Text("Home") },
            icon = { Icon(imageVector = Icons.Filled.Home, contentDescription = "Home") }
        )
        NavigationBarItem(
            selected = false, // Ganti dengan logika untuk menandai halaman aktif
            onClick = { navController.navigate("list") }, // Navigasi ke halaman list
            label = { Text("List") },
            icon = { Icon(imageVector = Icons.Filled.List, contentDescription = "List") }
        )
        NavigationBarItem(
            selected = false, // Ganti dengan logika untuk menandai halaman aktif
            onClick = { navController.navigate("settings") }, // Navigasi ke halaman settings
            label = { Text("Settings") },
            icon = { Icon(imageVector = Icons.Filled.Settings, contentDescription = "Settings") }
        )
    }
}

