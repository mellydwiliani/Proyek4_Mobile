package com.example.hanyarunrun.ui.screen.list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.hanyarunrun.ui.theme.HanyarunrunTheme
import com.example.hanyarunrun.viewmodel.DataViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DataListScreen(navController: NavHostController, viewModel: DataViewModel) {
    val dataList by viewModel.dataList.observeAsState(emptyList())
    val selectedTahun by viewModel.selectedTahun.observeAsState(2023)
    var selectedKabKota by remember { mutableStateOf<String?>(null) }
    var expandedKabKota by remember { mutableStateOf(false) }
    var expandedTahun by remember { mutableStateOf(false) }
    var searchQuery by remember { mutableStateOf("") }
    val kabKotaList = dataList.map { it.namaKabupatenKota }.distinct()
    val tahunList = dataList.map { it.tahun }.distinct().sorted()

    LaunchedEffect(selectedTahun) {
        viewModel.fetchAllDataFromApi(selectedTahun)
    }

    HanyarunrunTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = HanyarunrunTheme.colors.uiBackground
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Data Pendapatan per Kab/Kota",
                    style = MaterialTheme.typography.headlineMedium.copy(fontSize = 28.sp),
                    color = HanyarunrunTheme.colors.textPrimary,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(16.dp))

                ExposedDropdownMenuBox(
                    expanded = expandedTahun,
                    onExpandedChange = { expandedTahun = !expandedTahun },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    OutlinedTextField(
                        value = selectedTahun.toString(),
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Pilih Tahun") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor(),
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedTahun)
                        },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = HanyarunrunTheme.colors.brand,
                            unfocusedBorderColor = HanyarunrunTheme.colors.uiBorder,
                            focusedTextColor = HanyarunrunTheme.colors.textPrimary,
                            unfocusedTextColor = HanyarunrunTheme.colors.textPrimary
                        )
                    )
                    ExposedDropdownMenu(
                        expanded = expandedTahun,
                        onDismissRequest = { expandedTahun = false }
                    ) {
                        tahunList.forEach { tahun ->
                            DropdownMenuItem(
                                text = { Text(tahun.toString()) },
                                onClick = {
                                    viewModel.fetchAllDataFromApi(tahun)
                                    expandedTahun = false
                                }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                ExposedDropdownMenuBox(
                    expanded = expandedKabKota,
                    onExpandedChange = { expandedKabKota = !expandedKabKota },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    OutlinedTextField(
                        value = selectedKabKota ?: "Pilih Kab/Kota",
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Filter berdasarkan Kab/Kota") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor(),
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedKabKota)
                        },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = HanyarunrunTheme.colors.brand,
                            unfocusedBorderColor = HanyarunrunTheme.colors.uiBorder,
                            focusedTextColor = HanyarunrunTheme.colors.textPrimary,
                            unfocusedTextColor = HanyarunrunTheme.colors.textPrimary
                        )
                    )
                    ExposedDropdownMenu(
                        expanded = expandedKabKota,
                        onDismissRequest = { expandedKabKota = false }
                    ) {
                        DropdownMenuItem(
                            text = { Text("Semua Data") },
                            onClick = {
                                selectedKabKota = null
                                expandedKabKota = false
                            }
                        )
                        kabKotaList.forEach { kabKota ->
                            DropdownMenuItem(
                                text = { Text(kabKota) },
                                onClick = {
                                    selectedKabKota = kabKota
                                    expandedKabKota = false
                                }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    label = { Text("Cari Kab/Kota atau Sektor Wisata") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = HanyarunrunTheme.colors.brand,
                        unfocusedBorderColor = HanyarunrunTheme.colors.uiBorder,
                        focusedTextColor = HanyarunrunTheme.colors.textPrimary,
                        unfocusedTextColor = HanyarunrunTheme.colors.textPrimary
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))

                if (dataList.isEmpty()) {
                    Text(
                        text = "Tidak Ada Data Tersedia",
                        style = MaterialTheme.typography.bodyLarge,
                        color = HanyarunrunTheme.colors.textSecondary
                    )
                } else {
                    val filteredList = dataList.filter { item ->
                        (selectedKabKota == null || item.namaKabupatenKota == selectedKabKota) &&
                                (item.tahun == selectedTahun) &&
                                (searchQuery.isEmpty() ||
                                        item.namaKabupatenKota.contains(searchQuery, ignoreCase = true) ||
                                        item.sektorWisata.contains(searchQuery, ignoreCase = true))
                    }

                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(filteredList) { item ->
                            Card(
                                shape = RoundedCornerShape(12.dp),
                                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                                colors = CardDefaults.cardColors(containerColor = HanyarunrunTheme.colors.uiFloated),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Row(
                                    modifier = Modifier
                                        .background(HanyarunrunTheme.colors.uiFloated)
                                        .padding(16.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Column(
                                        modifier = Modifier.weight(1f)
                                    ) {
                                        Text(
                                            text = "Kab/Kota: ${item.namaKabupatenKota}",
                                            style = MaterialTheme.typography.titleMedium,
                                            color = HanyarunrunTheme.colors.textPrimary,
                                            fontWeight = FontWeight.Bold
                                        )
                                        Text(
                                            text = "Sektor: ${item.sektorWisata}",
                                            style = MaterialTheme.typography.bodyMedium,
                                            color = HanyarunrunTheme.colors.textSecondary
                                        )
                                        Text(
                                            text = "Total: ${item.jumlahPendapatan} ${item.satuan}",
                                            style = MaterialTheme.typography.bodyMedium,
                                            color = HanyarunrunTheme.colors.textSecondary
                                        )
                                        Text(
                                            text = "Tahun: ${item.tahun}",
                                            style = MaterialTheme.typography.bodyMedium,
                                            color = HanyarunrunTheme.colors.textSecondary
                                        )
                                    }
                                    IconButton(
                                        onClick = { viewModel.deleteData(item.id) }
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Delete,
                                            contentDescription = "Hapus Data",
                                            tint = MaterialTheme.colorScheme.error
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}