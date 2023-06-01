package ba.etf.rma23.projekat

import com.google.gson.annotations.SerializedName

data class Game(
    @SerializedName ("igdb_id") var id: Int,
    @SerializedName("name") var title: String?,
    @SerializedName ("platform") var platform: String?,
    @SerializedName("release_date") var releaseDate: String?,
    @SerializedName("rating") var rating: Double?,
    @SerializedName("cover_image") var coverImage: String?,
    @SerializedName("esrb_rating") var esrbRating: String?,
    @SerializedName("developer") var developer: String?,
    @SerializedName("publisher") var publisher: String?,
    @SerializedName("genre") var genre: String?,
    @SerializedName("description") var description: String?,
    @SerializedName("user_impressions") var userImpressions: List<UserImpression> = emptyList()
                )
