package ba.etf.rma23.projekat.data.repositories

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query


@Dao
interface GameReviewDao {
@Query("SELECT * FROM gamereview")
suspend fun getAll(): List<GameReview>
@Insert
suspend fun insertAll(vararg gamereviews: GameReview)

    @Query("SELECT * FROM gamereview WHERE online = 0")
    suspend fun getOfflineReviews(): List<GameReview>
}
