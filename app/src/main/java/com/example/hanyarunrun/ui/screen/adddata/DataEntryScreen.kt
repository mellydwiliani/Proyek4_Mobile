package com.example.hanyarunrun.ui.screen.adddata

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.hanyarunrun.data.DataEntity
import com.example.hanyarunrun.ui.theme.HanyarunrunTheme
import com.example.hanyarunrun.viewmodel.DataViewModel
import kotlinx.coroutines.launch

@Composable
fun DataEntryScreen(navController: NavHostController, viewModel: DataViewModel) {
    var namaKabupatenKota by remember { mutableStateOf("") }
    var sektorWisata by remember { mutableStateOf("") }
    var jumlahPendapatan by remember { mutableStateOf("") }
    var tahun by remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()

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
                    text = "Tambah Data Pendapatan",
                    style = MaterialTheme.typography.headlineMedium.copy(fontSize = 28.sp),
                    color = HanyarunrunTheme.colors.textPrimary,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(24.dp))

                Card(
                    shape = RoundedCornerShape(12.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                    colors = CardDefaults.cardColors(containerColor = HanyarunrunTheme.colors.uiFloated),
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(2.dp, HanyarunrunTheme.colors.brand, RoundedCornerShape(12.dp))
                ) {
                    Column(
                        modifier = Modifier
                            .background(HanyarunrunTheme.colors.uiFloated)
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Masukkan Data Baru",
                            style = MaterialTheme.typography.titleMedium,
                            color = HanyarunrunTheme.colors.textPrimary,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(12.dp))

                        OutlinedTextField(
                            value = namaKabupatenKota,
                            onValueChange = { namaKabupatenKota = it },
                            label = { Text("Nama Kabupaten/Kota") },
                            modifier = Modifier.fillMaxWidth(),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = HanyarunrunTheme.colors.brand,
                                unfocusedBorderColor = HanyarunrunTheme.colors.uiBorder,
                                focusedTextColor = HanyarunrunTheme.colors.textPrimary,
                                unfocusedTextColor = HanyarunrunTheme.colors.textPrimary
                            )
                        )
                        Spacer(modifier = Modifier.height(12.dp))

                        OutlinedTextField(
                            value = sektorWisata,
                            onValueChange = { sektorWisata = it },
                            label = { Text("Sektor Wisata") },
                            modifier = Modifier.fillMaxWidth(),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = HanyarunrunTheme.colors.brand,
                                unfocusedBorderColor = HanyarunrunTheme.colors.uiBorder,
                                focusedTextColor = HanyarunrunTheme.colors.textPrimary,
                                unfocusedTextColor = HanyarunrunTheme.colors.textPrimary
                            )
                        )
                        Spacer(modifier = Modifier.height(12.dp))

                        OutlinedTextField(
                            value = jumlahPendapatan,
                            onValueChange = { jumlahPendapatan = it.filter { char -> char.isDigit() || char == '.' } },
                            label = { Text("Jumlah Pendapatan") },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            modifier = Modifier.fillMaxWidth(),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = HanyarunrunTheme.colors.brand,
                                unfocusedBorderColor = HanyarunrunTheme.colors.uiBorder,
                                focusedTextColor = HanyarunrunTheme.colors.textPrimary,
                                unfocusedTextColor = HanyarunrunTheme.colors.textPrimary
                            )
                        )
                        Spacer(modifier = Modifier.height(12.dp))

                        OutlinedTextField(
                            value = tahun,
                            onValueChange = { tahun = it.filter { it.isDigit() } },
                            label = { Text("Tahun") },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            modifier = Modifier.fillMaxWidth(),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = HanyarunrunTheme.colors.brand,
                                unfocusedBorderColor = HanyarunrunTheme.colors.uiBorder,
                                focusedTextColor = HanyarunrunTheme.colors.textPrimary,
                                unfocusedTextColor = HanyarunrunTheme.colors.textPrimary
                            )
                        )
                        Spacer(modifier = Modifier.height(16.dp))

                        Button(
                            onClick = {
                                if (namaKabupatenKota.isNotBlank() && sektorWisata.isNotBlank() &&
                                    jumlahPendapatan.isNotBlank() && tahun.isNotBlank()
                                ) {
                                    val newData = DataEntity(
                                        id = 0, // Room akan generate ID jika autoGenerate = true
                                        kodeProvinsi = 32,
                                        namaProvinsi = "JAWA BARAT",
                                        kodeKabupatenKota = 3200 + (System.currentTimeMillis() % 100).toInt(),
                                        namaKabupatenKota = namaKabupatenKota,
                                        sektorWisata = sektorWisata,
                                        jumlahPendapatan = jumlahPendapatan.toDoubleOrNull() ?: 0.0,
                                        satuan = "RUPIAH",
                                        tahun = tahun.toIntOrNull() ?: 0
                                    )
                                    scope.launch {
                                        viewModel.addLocalData(newData)
                                        namaKabupatenKota = ""
                                        sektorWisata = ""
                                        jumlahPendapatan = ""
                                        tahun = ""
                                        navController.popBackStack()
                                    }
                                }
                            },
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = HanyarunrunTheme.colors.interactivePrimary.first(),
                                contentColor = HanyarunrunTheme.colors.textInteractive
                            )
                        ) {
                            Text(
                                text = "Tambah Data",
                                style = MaterialTheme.typography.bodyLarge,
                                color = HanyarunrunTheme.colors.textInteractive
                            )
                        }
                    }
                }
            }
        }
    }
}