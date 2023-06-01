package ba.etf.rma23.projekat.data.repositories

import ba.etf.rma23.projekat.Game
import ba.etf.rma23.projekat.ResponseGame
import com.google.gson.annotations.SerializedName
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface IGDBApi {

    data class AgeResponse(
        var id: Int,
        var rating: String)

    data class StringsResponse(
        @SerializedName("id") var id: Int,
        @SerializedName("name") var name: String
    )
    data class DateResponse(
        @SerializedName("id") var id: Int,
        @SerializedName("human") var human: String
    )


    @Headers(
        value =[
        "Accept: application/json",
        "Client-ID: j0glps4gilqsg4fdrdi8o5gpwwzl2s",
        "Authorization: Bearer g3obd0bk57rg9fvofvesvxhiipspbp"
                ]
    )
    @GET("games")
    suspend fun getGamesByName(
        @Query("search") name: String,
        @Query("fields") fields: String = "name,release_dates.human,age_ratings.rating,game_engines.name,genres.name,involved_companies.developer,platforms.name,cover.url,rating",
        @Query("limit") limit: Int =10
    ): Response<List<ResponseGame>>


    @Headers(
        value =[
            "Accept: application/json",
            "Client-ID: j0glps4gilqsg4fdrdi8o5gpwwzl2s",
            "Authorization: Bearer g3obd0bk57rg9fvofvesvxhiipspbp"
        ]
    )
    @GET("games/")
   suspend fun getGamesSafe(
        @Query("search") name: String,
        @Query("age_ratings.rating") ageRating: Int
    ): Response<List<Game>>

    @Headers(
        value =[
            "Accept: application/json",
            "Client-ID: j0glps4gilqsg4fdrdi8o5gpwwzl2s",
            "Authorization: Bearer g3obd0bk57rg9fvofvesvxhiipspbp"
        ]
    )
    @GET("games")
    suspend fun sortGames(
        @Query("sort") sort: String
    ): Response<List<Game>>
}