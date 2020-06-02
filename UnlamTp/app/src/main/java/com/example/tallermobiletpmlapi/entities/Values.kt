import com.google.gson.annotations.SerializedName




data class Values (

	@SerializedName("source") val source : Int,
	@SerializedName("id") val id : Int,
	@SerializedName("name") val name : String,
	@SerializedName("struct") val struct : String
)