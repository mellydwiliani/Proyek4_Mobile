package com.example.hanyarunrun.data.network

import com.example.hanyarunrun.data.ApiResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("api-backend/bigdata/disparbud/od_15380_jml_pendapatan_asli_drh_bidang_pariwisata__sektor__v1")
    suspend fun getPendapatanPariwisata(
        @Query("limit") limit: Int = 1000, // Default ke 1000 untuk mengambil lebih banyak data
        @Query("skip") skip: Int = 0,     // Untuk pagination
        @Query("tahun") tahun: Int? = null // Opsional untuk filter tahun
    ): ApiResponse
}