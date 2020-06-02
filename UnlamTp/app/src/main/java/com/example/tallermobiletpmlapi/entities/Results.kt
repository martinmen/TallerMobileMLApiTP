import com.google.gson.annotations.SerializedName

/*
Copyright (c) 2020 Kotlin Data Classes Generated from JSON powered by http://www.json2kotlin.com

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.

For support, please feel free to contact me at https://www.linkedin.com/in/syedabsar */


data class Results (

	@SerializedName("id") val id : String,
	@SerializedName("site_id") val site_id : String,
	@SerializedName("title") val title : String,
	@SerializedName("seller") val seller : Seller,
	@SerializedName("price") val price : Double,
	@SerializedName("currency_id") val currency_id : String,
	@SerializedName("available_quantity") val available_quantity : Int,
	@SerializedName("sold_quantity") val sold_quantity : Int,
	@SerializedName("buying_mode") val buying_mode : String,
	@SerializedName("listing_type_id") val listing_type_id : String,
	@SerializedName("stop_time") val stop_time : String,
	@SerializedName("condition") val condition : String,
	@SerializedName("permalink") val permalink : String,
	@SerializedName("thumbnail") val thumbnail : String,
	@SerializedName("accepts_mercadopago") val accepts_mercadopago : Boolean,
	@SerializedName("installments") val installments : Installments,
	//@SerializedName("address") val address : Address,
	//@SerializedName("shipping") val shipping : Shipping,
	//@SerializedName("seller_address") val seller_address : Seller_address,
	//@SerializedName("attributes") val attributes : List<Attributes>,
	//@SerializedName("differential_pricing") val differential_pricing : Differential_pricing,
	@SerializedName("original_price") val original_price : String,
	@SerializedName("category_id") val category_id : String,
	@SerializedName("official_store_id") val official_store_id : Int,
	@SerializedName("domain_id") val domain_id : String,
	@SerializedName("catalog_product_id") val catalog_product_id : String,
	@SerializedName("tags") val tags : List<String>,
	@SerializedName("catalog_listing") val catalog_listing : Boolean
)