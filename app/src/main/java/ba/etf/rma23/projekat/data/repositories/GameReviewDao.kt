package ba.etf.rma23.projekat.data.repositories

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update


@Dao
interface GameReviewDao {
@Query("SELECT * FROM gamereview")
suspend fun getAll(): List<GameReview>
@Insert
suspend fun insertAll(vararg gamereviews: GameReview)
@Query("SELECT COUNT(*) AS broj_reviews FROM gamereview WHERE online='false'")
suspend fun getOfflineReviews(): Int
    @Query("DELETE FROM gamereview")
    suspend fun deleteAll()
    @Update
    suspend fun update(review: GameReview)
}
