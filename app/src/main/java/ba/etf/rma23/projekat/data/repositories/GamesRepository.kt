package ba.etf.rma23.projekat.data.repositories

import android.annotation.SuppressLint
import ba.etf.rma23.projekat.Game
import ba.etf.rma23.projekat.GameData.Companion.getAll
import ba.etf.rma23.projekat.ResponseGame
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

object GamesRepository {
    @SuppressLint("SuspiciousIndentation")
    suspend fun getGamesByName(name: String): List<Game> {
        return withContext(Dispatchers.IO) {

            val response: Response<List<ResponseGame>> = IGDBApiConfig.retrofit.getGamesByName(name)
            val games: List<ResponseGame> = response.body() ?: emptyList()
            val games1: MutableList<Game> = mutableListOf()
            val i: Int =0

            for(i in 0 until games.size)
            {
                var id = games[i].id
                var title = games[i].name
                var esrbRating:String
                if(games[i].esrb==null)
                    esrbRating = "10"
                else {
                val response1: Response<List<IGDBApi.AgeResponse>> = IGDBApiConfig.retrofit.getAgeRating(games[i].esrb?.get(0) ?: 1)
                    esrbRating = response1.body()?.find { it.id == games[i].esrb?.get(0) ?: 2853 }?.rating.toString()

                }

              var releaseDate = games[i].release_date?.get(0)?.human
                //println(games[i].release_date?.get(0)?.human)
                val publisher = IGDBApiConfig.retrofit.getPublisher(games[i].publisher?.get(0) ?: 186).body()?.find {
                    it.id == (games[i].publisher?.get(0) ?: 186)
                }?.name.toString()


                val genre = IGDBApiConfig.retrofit.getGenre(games[i].genre?.get(0) ?: 4).body()?.find {
                    it.id == (games[i].genre?.get(
                        0
                    ) ?: 4)
                }?.name.toString()
              val developer = IGDBApiConfig.retrofit.getDeveloper(games[i].developer?.get(0) ?: 95000).body()?.find { it.id == games[i].developer?.get(0)?:95000 }?.name.toString()
              var platform = IGDBApiConfig.retrofit.getPlatform(games[i].platform?.get(0) ?: 39).body()?.find { it.id == games[i].platform?.get(0)?: 39 }?.name.toString()

              var rating = games[i].rating
              val description = games[i].description

                if(games[i].cover==null)
                    games[i].cover = 65483
              val coverImage =  IGDBApiConfig.retrofit.getCover(games[i].cover ?: 65483).body()?.find { it.id == games[i].cover?:65483 }?.name.toString()

                var game: Game = Game(id,title,platform,releaseDate,rating,coverImage,esrbRating,developer,publisher,genre,description)

                games1.add(game)


            }

            return@withContext games1
        }
    }
}