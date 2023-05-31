package ba.etf.rma23.projekat

import ba.etf.rma23.projekat.data.repositories.IGDBApi
import com.google.gson.annotations.SerializedName

data class ResponseGame (
    @SerializedName ("id") var id: Int?,
    @SerializedName ("name") var name: String?,
    @SerializedName ("age_ratings") var esrb: List<Int>?,
    @SerializedName ("release_dates") var release_date: List<IGDBApi.DateResponse>?,
    @SerializedName ("game_engines") var publisher: List<Int>?,
    @SerializedName ("genres") var genre: List<Int>?,
    @SerializedName ("involved_companies") var developer: List<Int>?,
    @SerializedName ("platforms") var platform: List<Int>?,
    @SerializedName ("rating") var rating: Double?,
    @SerializedName ("summary") var description: String?,
    @SerializedName ("cover") var cover: Int?
        )