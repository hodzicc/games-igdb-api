package ba.etf.rma23.projekat.data.repositories

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import ba.etf.rma23.projekat.Game
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object GameReviewsRepository {


    @SuppressLint("SuspiciousIndentation")
    suspend fun getReviewsForGame(igdbId: Int): List<GameReview> {
        return withContext(Dispatchers.IO) {
            val response = AccountApiConfig.retrofit.getGameReviews(igdbId)
                return@withContext response.body() ?: emptyList()
        }
    }

    suspend fun getOfflineReviews(context: Context): List<GameReview> {
        return withContext(Dispatchers.IO) {
            var db = AppDatabase.getInstance(context)
            var gamereviews = db.gameReviewDao().getOfflineReviews()

            return@withContext gamereviews
        }
    }

    suspend fun sendReview(context: Context,review: GameReview): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                val isFavorite = isGameFavorite(review.igdb_id)

                if (isFavorite) {
                    val response = AccountApiConfig.retrofit.postGameReview(
                        AccountGamesRepository.getHash(), review.igdb_id,
                        AccountApi.ReviewRequestBody(review.review, review.rating)
                    )
                    if (response.code() in 200..300) {
                        return@withContext true
                    } else {
                        review.online = false
                        saveOfflineReview(context, review)
                        return@withContext false
                    }
                } else {
                    val addedToFavorite = addToFav(context, review)
                    if (addedToFavorite) {

                        return@withContext sendReview(context, review)

                    } else {
                      //OVDJE ODE JER IMA TRY CATCH U OSTALIM METODAMA
                        review.online = false
                        saveOfflineReview(context, review)
                        return@withContext false
                    }
                }
            }
            catch(exception: Exception)
            {
                review.online = false
                saveOfflineReview(context, review)
                return@withContext false

            }
        }
    }
    private suspend fun isGameFavorite(igdbId: Int): Boolean {
        try {
            val favoriteGames = getFavoriteGames()
            return favoriteGames.any { it.id== igdbId }
        }
        catch(exception: Exception)
        {
             return false
        }

    }



    @SuppressLint("SuspiciousIndentation")
    private suspend fun getFavoriteGames(): List<Game> {

        val favoriteGames = AccountGamesRepository.getSavedGames()
            return favoriteGames
    }

    private suspend fun addToFav(context: Context, gamer: GameReview): Boolean {

        try {
            val game = GamesRepository.getGameById(gamer.igdb_id)
            AccountGamesRepository.saveGame(game)

            return true
        }
        catch(exception:Exception){
            return false
        }
    }

    private suspend fun saveOfflineReview(context: Context,review: GameReview) {

        val db = AppDatabase.getInstance(context)
        review.online=false
        db?.gameReviewDao()?.insertAll(review)


    }

    suspend fun sendOfflineReviews(context: Context): Int {
        return withContext(Dispatchers.IO) {
            val db = AppDatabase.getInstance(context)
            val offlineReviews = db?.gameReviewDao()?.getOfflineReviews()
            db.gameReviewDao().deleteAll()
            var sentReviews = 0

            offlineReviews?.forEach { review ->
                if(sendReview(context, review)) {
                    sentReviews++
                }
                else
                    db.gameReviewDao().insertAll(review)
            }

            return@withContext sentReviews
    }
}






}