package com.example.hanyarunrun.data

import com.google.gson.annotations.SerializedName

data class PendapatanPariwisata(
    @SerializedName("id") val id: Int,
    @SerializedName("kode_provinsi") val kodeProvinsi: Int,
    @SerializedName("nama_provinsi") val namaProvinsi: String,
    @SerializedName("kode_kabupaten_kota") val kodeKabupatenKota: Int,
    @SerializedName("nama_kabupaten_kota") val namaKabupatenKota: String,
    @SerializedName("sektor_wisata") val sektorWisata: String,
    @SerializedName("jumlah_pendapatan") val jumlahPendapatan: Double,
    @SerializedName("satuan") val satuan: String,
    @SerializedName("tahun") val tahun: Int
)