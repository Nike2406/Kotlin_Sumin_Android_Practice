package com.example.kotlin_sumin.pojo

import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose
import android.media.Rating

data class CoinInfo(
    @SerializedName("Id")
    @Expose
    val id: String? = null,

    @SerializedName("Name")
    @Expose
    val name: String? = null,

    @SerializedName("FullName")
    @Expose
    val fullName: String? = null,

    @SerializedName("ImageUrl")
    @Expose
    val imageUrl: String? = null,
)