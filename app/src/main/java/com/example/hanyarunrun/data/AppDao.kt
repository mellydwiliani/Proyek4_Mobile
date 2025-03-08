package com.example.hanyarunrun.data

import androidx.lifecycle.LiveData
import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface AppDao {
    @Query("SELECT * FROM profile_table LIMIT 1")
    fun getProfile(): LiveData<ProfileEntity?>

    @Query("SELECT * FROM profile_table LIMIT 1")
    suspend fun getProfileSync(): ProfileEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProfile(profile: ProfileEntity)

    @Query("UPDATE profile_table SET imagePath = :imagePath WHERE id = :id")
    suspend fun updateProfileImage(id: Int, imagePath: String)

    @Insert
    suspend fun insertPendapatan(pendapatan: DataEntity)

    @Query("SELECT * FROM pendapatan_table")
    fun getAllPendapatan(): Flow<List<DataEntity>>

    // Query baru untuk menghapus data berdasarkan ID
    @Query("DELETE FROM pendapatan_table WHERE id = :id")
    suspend fun deletePendapatanById(id: Int)
}