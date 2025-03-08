package com.example.hanyarunrun.data

import android.util.Log
import com.example.hanyarunrun.data.network.ApiService
import retrofit2.HttpException
import javax.inject.Inject

class PendapatanRepository @Inject constructor(
    private val apiService: ApiService
) {
    suspend fun getPendapatan(tahun: Int? = null, limit: Int = 1000, skip: Int = 0): List<PendapatanPariwisata> {
        try {
            val response = apiService.getPendapatanPariwisata(limit = limit, skip = skip, tahun = tahun)
            Log.d("PendapatanRepository", "API response for tahun $tahun, limit $limit, skip $skip: ${response.data.size} items, years: ${response.data.map { it.tahun }.distinct()}")
            return response.data
        } catch (e: HttpException) {
            Log.e("PendapatanRepository", "HTTP error: ${e.code()} - ${e.response()?.errorBody()?.string()}")
            throw e
        } catch (e: Exception) {
            Log.e("PendapatanRepository", "Other error: ${e.message}", e)
            throw e
        }
    }
}