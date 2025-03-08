package com.example.hanyarunrun.data

import com.google.gson.annotations.SerializedName

data class ApiResponse(
    @SerializedName("message") val message: String,
    @SerializedName("error") val error: Int,
    @SerializedName("data") val data: List<PendapatanPariwisata>
)