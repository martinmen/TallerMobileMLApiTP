package com.example.pruebastp.data

import com.example.tallermobiletpmlapi.entities.SearchResult
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MercadoLibreAPI{

@GET("items/{itemId}")
fun getitem(@Path("itemId") itemId:String): Call<Article>

    @GET("sites/MLA/search")
    fun search(@Query("q") q: String): Call<SearchResult>
}