package ba.etf.rma23.projekat.data.repositories

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import ba.etf.rma23.projekat.*
import okhttp3.ResponseBody
import retrofit2.http.Body
import retrofit2.http.Path

interface AccountApi {

    @POST("account/{aid}/game")
    suspend fun saveGame(
        @Path("aid") aid: String,
        @Body game:Game
    ): Response<ResponseAccGame>

    @GET("account/{aid}/games")
    suspend fun getSavedGames(
        @Path("aid") aid: String
    ): Response<List<ResponseAccGame>>




}