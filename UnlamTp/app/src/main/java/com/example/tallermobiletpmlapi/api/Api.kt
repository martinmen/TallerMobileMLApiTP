package com.example.pruebastp.data

import MercadoLibreAPI
import com.example.tallermobiletpmlapi.entities.DescriptionArticle
import com.example.tallermobiletpmlapi.entities.Article
import com.example.tallermobiletpmlapi.entities.SearchResult
import com.google.gson.Gson
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Api {
    private val oldValue = " "
    private val newValue = "%20"
    private val baseUrlApiMLA = "https://api.mercadolibre.com"

    fun getApi(): MercadoLibreAPI {
        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrlApiMLA)
            .addConverterFactory(GsonConverterFactory.create(Gson()))
            .build()
        return retrofit.create(MercadoLibreAPI::class.java)
    }

    fun getArticle(id: String, callback: Callback<Article>) {
        getApi().getitem(id).enqueue(callback)
    }

    fun getArticleDescription(
        id: String,
        callback: Callback<Array<DescriptionArticle>>
    ) {
        getApi().getitemDescription(id).enqueue(callback)
    }

    fun getListArticle(q: String, callback: Callback<SearchResult>) {
        getApi().search(q.replace(oldValue, newValue)).enqueue(callback)
    }
}