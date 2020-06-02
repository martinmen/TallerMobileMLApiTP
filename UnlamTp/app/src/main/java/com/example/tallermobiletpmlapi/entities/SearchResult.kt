package com.example.tallermobiletpmlapi.entities

import Paging
import Results
import com.google.gson.annotations.SerializedName

data class SearchResult (
    @SerializedName("site_id") val site_id : String,
    @SerializedName("query") val query : String,
    @SerializedName("paging") val paging : Paging,
    @SerializedName("results") val results : ArrayList<Results>
)