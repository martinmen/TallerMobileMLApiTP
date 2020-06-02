package com.example.pruebastp.data

import com.example.tptallerdiseniomlapi.entities.Pictures
import com.google.gson.annotations.SerializedName
import retrofit2.http.Url

data class Article(
    val title: String,
    val thumbnail: String,
    val price: Int,
    val descripcion: String,
    @SerializedName("pictures") val pictures: Array<Pictures>
)