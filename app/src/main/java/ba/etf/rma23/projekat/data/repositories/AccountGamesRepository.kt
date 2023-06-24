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
                 for(j in list)
                 {
                     if(j.id==games[i].igdbId)
                     { games1.add(j)
                     break;
                     }
                 }

             }
  //          println("pozvana saved")
            GamesRepository.lista = games1
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
        var ageRating = arrayOf(3, 7, 12, 16, 18, 1, 3, 1, 10, 13, 17, 18)
        if(getAge() == 0)
            return true
        val games = getSavedGames()
        for(i in games.indices)
        {
            if(games[i].esrbRating == "null")
                continue
            var value = (games[i].esrbRating?.toDouble()?.minus(1))?.toInt()
            if (value != null) {
                if(value > 12)
                    continue
            }
            if(ageRating[value!!] > getAge())
                if(!removeGame(games[i].id)!!)
                    return false
        }
        return true

    }


}