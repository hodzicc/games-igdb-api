package ba.etf.rma23.projekat.data.repositories

import ba.etf.rma23.projekat.Game
import ba.etf.rma23.projekat.ResponseGame
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface IGDBApi {

    data class AgeResponse(
        var id: Int,
        var rating: String)

    data class PlatformResponse(
        var id: Int,
        var name: String
    )
    @Headers(
        value =[
            "Accept: application/json",
            "Client-ID: j0glps4gilqsg4fdrdi8o5gpwwzl2s",
            "Authorization: Bearer g3obd0bk57rg9fvofvesvxhiipspbp"
        ]
    )
    @GET("age_ratings")
    suspend fun getAgeRating(
        @Query("id") id: Int,
        @Query("fields") fields: String ="rating"
    ): Response<List<AgeResponse>>

    @Headers(
        value =[
            "Accept: application/json",
            "Client-ID: j0glps4gilqsg4fdrdi8o5gpwwzl2s",
            "Authorization: Bearer g3obd0bk57rg9fvofvesvxhiipspbp"
        ]
    )
    @GET("game_engines")
    suspend fun getPublisher(
        @Query("id") id: Int,
        @Query("fields") fields:String ="name"
    ): Response<List<PlatformResponse>>

    @Headers(
        value =[
            "Accept: application/json",
            "Client-ID: j0glps4gilqsg4fdrdi8o5gpwwzl2s",
            "Authorization: Bearer g3obd0bk57rg9fvofvesvxhiipspbp"
        ]
    )
    @GET("genres")
    suspend fun getGenre(
        @Query("id") id: Int,
        @Query("fields") fields:String ="name"
    ): Response<List<PlatformResponse>>

    @Headers(
        value =[
            "Accept: application/json",
            "Client-ID: j0glps4gilqsg4fdrdi8o5gpwwzl2s",
            "Authorization: Bearer g3obd0bk57rg9fvofvesvxhiipspbp"
        ]
    )
    @GET("involved_companies")
    suspend fun getDeveloper(
        @Query("id") id: Int,
        @Query("fields") fields:String ="developer"
    ): Response<List<PlatformResponse>>

    @Headers(
        value =[
            "Accept: application/json",
            "Client-ID: j0glps4gilqsg4fdrdi8o5gpwwzl2s",
            "Authorization: Bearer g3obd0bk57rg9fvofvesvxhiipspbp"
        ]
    )
    @GET("platforms")
    suspend fun getPlatform(
        @Query("id") id: Int,
        @Query("fields") fields:String ="name"
    ): Response<List<PlatformResponse>>

    @Headers(
        value =[
            "Accept: application/json",
            "Client-ID: j0glps4gilqsg4fdrdi8o5gpwwzl2s",
            "Authorization: Bearer g3obd0bk57rg9fvofvesvxhiipspbp"
        ]
    )
    @GET("covers")
    suspend fun getCover(
        @Query("id") id: Int,
        @Query("fields") fields:String ="url"
    ): Response<List<PlatformResponse>>

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
        @Query("fields") fields:String ="name,age_ratings,first_release_date,game_engines,genres,involved_companies,platforms,rating,summary,cover",
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