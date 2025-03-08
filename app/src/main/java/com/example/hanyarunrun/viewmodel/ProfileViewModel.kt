package com.example.hanyarunrun.viewmodel

import android.app.Application
import android.content.Context
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.hanyarunrun.data.AppDao
import com.example.hanyarunrun.data.AppDatabase
import com.example.hanyarunrun.data.ProfileEntity
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream

class ProfileViewModel(application: Application) : AndroidViewModel(application) {
    private val dao = AppDatabase.getDatabase(application).appDao()
    private val _profileEntity = MutableLiveData<ProfileEntity?>()
    val profileEntity: LiveData<ProfileEntity?> = _profileEntity

    init {
        // Inisialisasi profil default jika belum ada
        viewModelScope.launch {
            val profile = dao.getProfileSync() // Mengambil data secara sinkron untuk init
            if (profile == null) {
                val defaultProfile = ProfileEntity(
                    id = 1,
                    studentName = "Nama Mahasiswa",
                    studentEmail = "mahasiswa@example.com"
                )
                dao.insertProfile(defaultProfile)
                _profileEntity.postValue(defaultProfile)
            } else {
                _profileEntity.postValue(profile)
            }
        }
    }

    fun upsertProfile(id: Int, name: String, email: String) {
        viewModelScope.launch {
            val currentData = dao.getProfileSync() // Ambil data langsung dari DB
            val profile = currentData?.copy(
                studentName = name,
                studentEmail = email,
                imagePath = currentData.imagePath // Pertahankan imagePath jika ada
            ) ?: ProfileEntity(
                id = id,
                studentName = name,
                studentEmail = email
            )
            dao.insertProfile(profile)
            _profileEntity.postValue(profile) // Perbarui LiveData secara manual
        }
    }

    fun updateProfileImage(id: Int, imagePath: String) {
        viewModelScope.launch {
            dao.updateProfileImage(id, imagePath)
            val updatedProfile = dao.getProfileSync()?.copy(imagePath = imagePath)
            _profileEntity.postValue(updatedProfile)
        }
    }

    fun saveImageToInternalStorage(context: Context, uri: Uri): String {
        val fileName = "profile_${System.currentTimeMillis()}.jpg"
        val file = File(context.filesDir, fileName)
        try {
            val inputStream: InputStream? = context.contentResolver.openInputStream(uri)
            val outputStream = FileOutputStream(file)
            inputStream?.use { input ->
                outputStream.use { output ->
                    input.copyTo(output)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return file.absolutePath
    }
}

// Tambahkan fungsi sinkron ke AppDao
suspend fun AppDao.getProfileSync(): ProfileEntity? {
    return getProfile().value
}