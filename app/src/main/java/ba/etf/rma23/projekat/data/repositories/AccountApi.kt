package ba.etf.rma23.projekat.data.repositories

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import ba.etf.rma23.projekat.*
import com.google.gson.annotations.SerializedName
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.Path

interface AccountApi {
    data class ResponseAccGame1 (
        @SerializedName("id") var id: Int?,
        @SerializedName("igdb_id") var igdbId: Int?,
        @SerializedName("name") var title: String?,
        @SerializedName("updatedAt") var updatedAt: String?,
        @SerializedName("createdAt") var createdAt: String?
    )
    data class SaveGameRequest(
        @SerializedName("game") val game: GameData
    )
    data class GameData(
        @SerializedName("igdb_id") val igdbId: Int?,
        @SerializedName("name") val name: String?
    )

    data class ReviewRequestBody(
       @SerializedName("review") val review: String?,
       @SerializedName ("rating") val rating: Int?
    )


    data class DeleteGameResponse(
        @SerializedName ("success") val succ: String?
    )

    data class DeleteGameReviewResponse(
        @SerializedName ("success") val succ: Boolean?,
        @SerializedName ("obrisano") val deleted: Int?
    )

    @POST("account/{aid}/game")
    suspend fun saveGame(
        @Path("aid") aid: String,
        @Body game:SaveGameRequest
    ): Response<ResponseAccGame1>



    @GET("account/{aid}/games")
    suspend fun getSavedGames(
        @Path("aid") aid: String
    ): Response<List<ResponseAccGame>>

    @DELETE("account/{aid}/game/{gid}")
    suspend fun deleteGame(
        @Path("aid") id: String,
        @Path("gid") igdb_id: Int
    ) : Response<DeleteGameResponse>

    @POST("account/{aid}/game/{gid}/gamereview")
    suspend fun postGameReview(
        @Path("aid") id: String,
        @Path("gid") igdb_id: Int,
        @Body review: ReviewRequestBody
    ) : Response<GameReview>

    @GET("game/{gid}/gamereviews")
    suspend fun getGameReviews(
        @Path("gid") igdb_id: Int
    ) : Response<List<GameReview>>

    @DELETE("account/{aid}/gamereviews")
    suspend fun deleteGameReviews(
        @Path("aid") id: String
    ) : Response<DeleteGameReviewResponse>



}