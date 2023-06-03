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
        if(AccountGamesRepository.getAge()==0)
            return true
        val games = getSavedGames()
        for(i in games.indices)
        {
            if(games[i].esrbRating!=null)
            {
                var value = games[i].esrbRating
                var value1: Int=1
                if(value=="1.0") value1=3
                else if(value=="2.0") value1=7
                else if(value=="3.0") value1=12
                else if(value=="4.0") value1=16
                else if(value=="5.0") value1=18
                else if(value=="6.0") value1=1
                else if(value=="7.0") value1=3
                else if(value=="8.0") value1=1
                else if(value=="9.0") value1=10
                else if(value=="10.0") value1=13
                else if(value=="11.0") value1=17
                else if(value=="12.0") value1=18
                else {
                    value1=1
                }

                if(value1>getAge())
                {
                    removeGame(games[i].id)

                }
            }
        }
        return true
    }


}