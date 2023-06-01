package ba.etf.rma23.projekat.data.repositories

import android.annotation.SuppressLint
import ba.etf.rma23.projekat.Game
import com.google.gson.annotations.SerializedName
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.math.roundToInt

object GamesRepository {
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
            return@withContext games
        }

    }

    @SuppressLint("SuspiciousIndentation")
    suspend fun getGamesSafe(name:String):List<Game>{
        if(AccountGamesRepository.getAge()==0)
            return listOf()
        val games = getGamesByName(name)
        val games1: MutableList<Game> = mutableListOf()
        val i:Int =0
        for(i in games.indices)
        {
            if(games[i].esrbRating==null)
            {
                games1.add(games[i])
            }
            else
            {
                if((games[i].esrbRating?.toDoubleOrNull()
                        ?: 0.0) <= AccountGamesRepository.getAge()
                )
               // println(games[i].esrbRating)
                games1.add(games[i])
            }
        }
        return games1
    }

}