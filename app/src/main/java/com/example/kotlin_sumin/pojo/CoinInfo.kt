package com.example.kotlin_sumin.pojo

import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose
import android.media.Rating

data class CoinInfo(
    @SerializedName("Name")
    @Expose
    val name: String? = null,
)