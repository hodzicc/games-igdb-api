package ba.etf.rma23.projekat

import com.google.gson.annotations.SerializedName

data class ResponseAccGame (
   // @SerializedName("id") var id: Int?,
    @SerializedName("igdb_id") var igdbId: Int?,
    @SerializedName("name") var title: String?
)