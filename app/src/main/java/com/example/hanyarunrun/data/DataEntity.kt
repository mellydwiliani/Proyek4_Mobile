package com.example.hanyarunrun.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pendapatan_table")
data class DataEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val kodeProvinsi: Int,
    val namaProvinsi: String,
    val kodeKabupatenKota: Int,
    val namaKabupatenKota: String,
    val sektorWisata: String,
    val jumlahPendapatan: Double,
    val satuan: String,
    val tahun: Int
)