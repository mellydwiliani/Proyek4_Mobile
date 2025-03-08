package com.example.hanyarunrun.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hanyarunrun.data.DataEntity
import com.example.hanyarunrun.data.PendapatanPariwisata
import com.example.hanyarunrun.data.PendapatanRepository
import com.example.hanyarunrun.data.RoomRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DataViewModel @Inject constructor(
    private val apiRepository: PendapatanRepository,
    private val roomRepository: RoomRepository
) : ViewModel() {

    private val _dataList = MutableLiveData<List<PendapatanPariwisata>>(emptyList())
    val dataList: LiveData<List<PendapatanPariwisata>> get() = _dataList

    private val _selectedTahun = MutableLiveData<Int>(2023)
    val selectedTahun: LiveData<Int> get() = _selectedTahun

    init {
        viewModelScope.launch {
            try {
                roomRepository.getAllPendapatan().collectLatest { roomData ->
                    val combinedData = mutableListOf<PendapatanPariwisata>()
                    combinedData.addAll(roomData.map { it.toPendapatanPariwisata() })
                    _dataList.value?.let { combinedData.addAll(it.filter { apiData ->
                        roomData.none { it.id == apiData.id }
                    }) }
                    _dataList.postValue(combinedData)
                }
                fetchAllDataFromApi()
            } catch (e: Exception) {
                _dataList.postValue(emptyList())
            }
        }
    }

    fun fetchAllDataFromApi(tahun: Int = 2023) {
        viewModelScope.launch {
            try {
                _selectedTahun.value = tahun
                var skip = 0
                val allApiData = mutableListOf<PendapatanPariwisata>()
                while (true) {
                    val apiData = apiRepository.getPendapatan(tahun, limit = 1000, skip = skip)
                    if (apiData.isEmpty()) break
                    allApiData.addAll(apiData)
                    skip += 1000
                }
                val currentData = _dataList.value.orEmpty().toMutableList()
                val roomData = currentData.filter { it.id in roomRepository.getAllPendapatan().firstOrNull()?.map { it.id } ?: emptyList() }
                currentData.clear()
                currentData.addAll(roomData)
                currentData.addAll(allApiData.filter { apiItem ->
                    currentData.none { it.id == apiItem.id }
                })
                _dataList.postValue(currentData)
            } catch (e: Exception) {
                _dataList.postValue(_dataList.value.orEmpty())
            }
        }
    }

    fun addLocalData(data: DataEntity) {
        viewModelScope.launch {
            roomRepository.insertPendapatan(data)
        }
    }

    // Fungsi baru untuk menghapus data
    fun deleteData(id: Int) {
        viewModelScope.launch {
            try {
                roomRepository.deletePendapatanById(id)
                val currentData = _dataList.value.orEmpty().toMutableList()
                currentData.removeAll { it.id == id }
                _dataList.postValue(currentData)
            } catch (e: Exception) {
                // Handle error jika diperlukan
            }
        }
    }

    private fun DataEntity.toPendapatanPariwisata(): PendapatanPariwisata {
        return PendapatanPariwisata(
            id = id,
            kodeProvinsi = kodeProvinsi,
            namaProvinsi = namaProvinsi,
            kodeKabupatenKota = kodeKabupatenKota,
            namaKabupatenKota = namaKabupatenKota,
            sektorWisata = sektorWisata,
            jumlahPendapatan = jumlahPendapatan,
            satuan = satuan,
            tahun = tahun
        )
    }
}