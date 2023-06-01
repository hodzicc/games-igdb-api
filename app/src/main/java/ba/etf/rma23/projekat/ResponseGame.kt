package ba.etf.rma23.projekat

import ba.etf.rma23.projekat.data.repositories.GamesRepository
import ba.etf.rma23.projekat.data.repositories.IGDBApi
import com.google.gson.annotations.SerializedName

data class ResponseGame (
    @SerializedName("id") var id: Int,
    @SerializedName ("name") var name: String?,
    @SerializedName ("age_ratings") var esrb: List<GamesRepository.RatingResponse>?,
    @SerializedName ("release_dates") var release_date: List<GamesRepository.DateResponse>?,
    @SerializedName ("game_engines") var publisher: List<GamesRepository.NameResponse>?,
    @SerializedName ("genres") var genre: List<GamesRepository.NameResponse>?,
    @SerializedName ("involved_companies") var developer: List<GamesRepository.DeveloperResponse>?,
    @SerializedName ("platforms") var platform: List<GamesRepository.NameResponse>?,
    @SerializedName ("rating") var rating: Double?,
    @SerializedName ("summary") var description: String?,
    @SerializedName ("cover") var cover: GamesRepository.URLResponse?
        )