package ba.etf.rma23.projekat.data.repositories

import android.annotation.SuppressLint
import ba.etf.rma23.projekat.Game
import ba.etf.rma23.projekat.ResponseAccGame
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

object AccountGamesRepository {
    private lateinit var accHash: String
    private var accAge: Int = 0

    public fun setHash(acHash:String):Boolean{
        accHash = acHash
        return true;
    }
    public fun getHash():String{
        return accHash;
    }
    public fun setAge(age:Int):Boolean{
        accAge=age
        if(accAge<3 || accAge>100)
            return false
        return true
    }

    suspend fun saveGame(game:Game):Game {
        return withContext(Dispatchers.IO) {
         //   println("uso1")
        AccountApiConfig.retrofit.saveGame(getHash(), game)
         //   println("uso")
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


}