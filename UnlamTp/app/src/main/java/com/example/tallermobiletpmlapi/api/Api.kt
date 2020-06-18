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

    fun getApi():MercadoLibreAPI{
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.mercadolibre.com")
            .addConverterFactory(GsonConverterFactory.create(Gson()))
            .build()
        return  retrofit.create(MercadoLibreAPI::class.java)
    }

    fun getArticle(id: String,callback: Callback<Article>){
        getApi().getitem(id).enqueue(callback)
    }
    fun getArticleDescription(id: String,callback: Callback<DescriptionArticle>){
        getApi().getitemDescription(id).enqueue(callback)
    }

    fun getListArticle(q: String,callback: Callback<SearchResult>){
        getApi().search(q.replace( " ", "%20")).enqueue(callback)
    }
}