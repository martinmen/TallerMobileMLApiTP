import com.google.gson.annotations.SerializedName


data class Results(
    @SerializedName("id") val id: String,
    @SerializedName("site_id") val site_id: String,
    @SerializedName("title") val title: String,
    @SerializedName("seller") val seller: Seller,
    @SerializedName("price") val price: Double,
    @SerializedName("currency_id") val currency_id: String,
    @SerializedName("available_quantity") val available_quantity: Int,
    @SerializedName("sold_quantity") val sold_quantity: Int,
    @SerializedName("buying_mode") val buying_mode: String,
    @SerializedName("listing_type_id") val listing_type_id: String,
    @SerializedName("stop_time") val stop_time: String,
    @SerializedName("condition") val condition: String,
    @SerializedName("permalink") val permalink: String,
    @SerializedName("thumbnail") val thumbnail: String,
    @SerializedName("accepts_mercadopago") val accepts_mercadopago: Boolean,
    @SerializedName("installments") val installments: Installments,
    //@SerializedName("address") val address : Address,
    //@SerializedName("shipping") val shipping : Shipping,
    //@SerializedName("seller_address") val seller_address : Seller_address,
    //@SerializedName("attributes") val attributes : List<Attributes>,
    //@SerializedName("differential_pricing") val differential_pricing : Differential_pricing,
    @SerializedName("original_price") val original_price: String,
    @SerializedName("category_id") val category_id: String,
    @SerializedName("official_store_id") val official_store_id: Int,
    @SerializedName("domain_id") val domain_id: String,
    @SerializedName("catalog_product_id") val catalog_product_id: String,
    @SerializedName("tags") val tags: List<String>,
    @SerializedName("catalog_listing") val catalog_listing: Boolean
)