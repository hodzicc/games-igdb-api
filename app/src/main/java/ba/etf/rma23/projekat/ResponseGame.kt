package ba.etf.rma23.projekat

import com.google.gson.annotations.SerializedName

data class ResponseGame (
    @SerializedName ("id") var id: Int?,
    @SerializedName ("name") var name: String?,
    @SerializedName ("age_ratings") var esrb: List<Int>?,
    @SerializedName ("first_release_date") var release_date: String?,
    @SerializedName ("game_engines") var publisher: List<Int>?,
    @SerializedName ("genres") var genre: List<Int>?,
    @SerializedName ("involved_companies") var developer: List<Int>?,
    @SerializedName ("platforms") var platform: List<Int>?,
    @SerializedName ("rating") var rating: Double?,
    @SerializedName ("summary") var description: String?,
    @SerializedName ("cover") var cover: Int?
        )