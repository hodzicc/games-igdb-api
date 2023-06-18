package ba.etf.rma23.projekat.data.repositories

import android.annotation.SuppressLint
import android.content.Context
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
            val db = AppDatabase.getInstance(context)
            val gamereviews = db.gameReviewDao().getOfflineReviews()
            return@withContext gamereviews
        }
    }





}