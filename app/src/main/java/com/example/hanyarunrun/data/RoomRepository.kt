package com.example.hanyarunrun.data

import androidx.lifecycle.LiveData
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RoomRepository @Inject constructor(
    private val appDao: AppDao
) {
    suspend fun insertPendapatan(pendapatan: DataEntity) {
        appDao.insertPendapatan(pendapatan)
    }

    fun getAllPendapatan(): Flow<List<DataEntity>> {
        return appDao.getAllPendapatan()
    }

    // Fungsi baru untuk menghapus data berdasarkan ID
    suspend fun deletePendapatanById(id: Int) {
        appDao.deletePendapatanById(id)
    }
}