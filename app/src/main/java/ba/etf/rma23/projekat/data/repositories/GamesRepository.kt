package ba.etf.rma23.projekat.data.repositories

import android.annotation.SuppressLint
import ba.etf.rma23.projekat.Game
import ba.etf.rma23.projekat.data.repositories.AccountGamesRepository.getAge
import ba.etf.rma23.projekat.data.repositories.AccountGamesRepository.getSavedGames
import com.google.gson.annotations.SerializedName
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.math.roundToInt

object GamesRepository {
    public var lista: List<Game> =listOf()
    data class DateResponse(
        @SerializedName("id") var id: Int,
        @SerializedName("human") var human: String
    )

    data class RatingResponse(
        @SerializedName("id") var id: Int,
        @SerializedName("rating") var rating: Double
    )

    data class NameResponse(
        @SerializedName("id") var id: Int,
        @SerializedName("name") var name: String
    )

    data class DeveloperResponse(
        @SerializedName("id") var id: Int,
        @SerializedName("developer") var developer: String
    )

    data class URLResponse(
        @SerializedName("id") var id: Int,
        @SerializedName("url") var url: String
    )
    suspend fun getGamesByName(name:String):List<Game>{
        return withContext(Dispatchers.IO) {
            val response = IGDBApiConfig.retrofit.getGamesByName(name)
            val games1 = response.body() ?: emptyList()
            val games: MutableList<Game> = mutableListOf()

            for(i in games1.indices){
                val id = games1[i].id
                val title = games1[i].name
                val esrbRating = games1[i].esrb?.get(0)?.rating.toString()
                val releaseDate = games1[i].release_date?.get(0)?.human
                val publisher = games1[i].publisher?.get(0)?.name
                val genre = games1[i].genre?.get(0)?.name
                val developer = games1[i].developer?.get(0)?.developer
                val platform = games1[i].platform?.get(0)?.name
                val rating = (games1[i].rating?.times(10))?.roundToInt()?.div(10.0)
                val description = games1[i].description
                val coverImage = games1[i].cover?.url
                val game: Game = Game(id,title,platform,releaseDate.toString(),rating,coverImage,esrbRating,developer,publisher,genre,description, listOf())

              //  println(esrbRating)
                games.add(game)
            }
            lista=games
      //      println("pozvana by name")
            return@withContext games
        }

    }

    suspend fun getGameById(id: Int): Game{
        return withContext(Dispatchers.IO) {
            val response = IGDBApiConfig.retrofit.getGamesById(id)
            val games1 = response.body() ?: emptyList()
            val games: MutableList<Game> = mutableListOf()

            for(i in games1.indices){
                val id = games1[i].id
                val title = games1[i].name
                val esrbRating = games1[i].esrb?.get(0)?.rating.toString()
                val releaseDate = games1[i].release_date?.get(0)?.human
                val publisher = games1[i].publisher?.get(0)?.name
                val genre = games1[i].genre?.get(0)?.name
                val developer = games1[i].developer?.get(0)?.developer
                val platform = games1[i].platform?.get(0)?.name
                val rating = (games1[i].rating?.times(10))?.roundToInt()?.div(10.0)
                val description = games1[i].description
                val coverImage = games1[i].cover?.url
                val game: Game = Game(id,title,platform,releaseDate.toString(),rating,coverImage,esrbRating,developer,publisher,genre,description, listOf())

                //  println(esrbRating)
                games.add(game)
            }

            return@withContext games[0]
        }
    }
    @SuppressLint("SuspiciousIndentation")
    suspend fun getGamesSafe(name:String):List<Game>{
        if(AccountGamesRepository.getAge()==0)
            return listOf()
        val games = getGamesByName(name)
        val games1: MutableList<Game> = mutableListOf()
//println(games.size)
        for(i in games.indices)
        {
           // println("bla")
           // println(games[i].esrbRating)
            if(games[i].esrbRating==null)
            {
                games1.add(games[i])
            }
            else
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

                if(value1<=getAge())
                {
                    games1.add(games[i])

                }
            }
        }
        lista=games1
        return games1
    }

    suspend fun sortGames():List<Game>{
        val listica = GamesRepository.lista
        val savedGames = getSavedGames()
        val favorites = savedGames.associateBy { it.title }
        val sortedGames = mutableListOf<Game>()

        lista = listica
        for (game in savedGames) {
            if (lista.contains(game)) {
                sortedGames.add(game)
            }
        }
        sortedGames.sortBy { it.title }
        val remainingGames = mutableListOf<Game>()
        for(game in lista)
        {
            if(!sortedGames.contains(game))
                remainingGames.add(game)
        }
        remainingGames.sortBy { it.title }
        sortedGames.addAll(remainingGames)

        return sortedGames
    }

}