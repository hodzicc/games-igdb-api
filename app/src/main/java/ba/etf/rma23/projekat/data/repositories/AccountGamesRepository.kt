package ba.etf.rma23.projekat.data.repositories

import android.annotation.SuppressLint
import ba.etf.rma23.projekat.Game
import ba.etf.rma23.projekat.ResponseAccGame
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import retrofit2.Response
import kotlin.properties.Delegates

object AccountGamesRepository {
    private lateinit var accHash: String
    private var accAge: Int =0

    public fun setHash(acHash:String):Boolean{
        accHash = acHash
        return true;
    }
    public fun getHash():String{
        return accHash;
    }
    public fun getAge():Int{
        return accAge;
    }
    public fun setAge(age:Int):Boolean{

        if(age<3 || age>100)
            return false
        accAge=age
        return true
    }

    suspend fun saveGame(game:Game):Game {
        return withContext(Dispatchers.IO) {

            val gameData = AccountApi.GameData(game.id, game.title)
            val request = AccountApi.SaveGameRequest(gameData)
        AccountApiConfig.retrofit.saveGame(getHash(), request)

            return@withContext game
        }
    }

    @SuppressLint("SuspiciousIndentation")
    suspend fun getSavedGames():List<Game> {
        return withContext(Dispatchers.IO) {
             val response: Response<List<ResponseAccGame>> = AccountApiConfig.retrofit.getSavedGames(getHash())
            val games: List<ResponseAccGame> = response.body() ?: emptyList()
            val games1: MutableList<Game> = mutableListOf()
             for(i in 0 until games.size){
             val list = GamesRepository.getGamesByName(games[i].title.toString())
                 games1.add(list[0])
             }
            return@withContext games1
        }
    }

    suspend fun getGamesContainingString(query: String): List<Game> {
        return withContext(Dispatchers.IO) {
            val savedGames = getSavedGames()
            val filteredGames = savedGames.filter { game ->
                game.title!!.contains(query, ignoreCase = true)
            }
            return@withContext filteredGames
        }
    }
    suspend fun removeGame(id: Int): Boolean? {
        var str = AccountApiConfig.retrofit.deleteGame(getHash(), id).body()
        if(str?.succ=="Games deleted")
            return true
        return false
    }

    suspend fun removeNonSafe():Boolean{
        val games1 = getSavedGames()
        val i: Int =0
        for(i in games1.indices)
        {
            if(games1[i].esrbRating==null || games1[i].esrbRating?.toInt()!! <= getAge())

                games1[i].id?.let { removeGame(it) }
        }
        return true
    }


}