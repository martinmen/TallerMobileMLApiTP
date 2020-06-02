package com.example.tptallerdiseniomlapi.entities

import com.google.gson.annotations.SerializedName



data class Pictures (

	@SerializedName("id") val id : String,
	@SerializedName("url") val url : String,
	@SerializedName("secure_url") val secure_url : String,
	@SerializedName("size") val size : String,
	@SerializedName("max_size") val max_size : String,
	@SerializedName("quality") val quality : String
)
