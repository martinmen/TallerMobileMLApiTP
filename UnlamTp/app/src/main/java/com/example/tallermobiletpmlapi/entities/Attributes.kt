import com.google.gson.annotations.SerializedName

data class Attributes (

	@SerializedName("values") val values : List<Values>,
	@SerializedName("attribute_group_id") val attribute_group_id : String,
	//@SerializedName("source") val source : Long,
	@SerializedName("id") val id : String,
	@SerializedName("name") val name : String,
	@SerializedName("value_id") val value_id : Int,
	@SerializedName("value_name") val value_name : String,
	@SerializedName("value_struct") val value_struct : String,
	@SerializedName("attribute_group_name") val attribute_group_name : String
)