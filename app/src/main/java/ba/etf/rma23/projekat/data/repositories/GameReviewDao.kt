package ba.etf.rma23.projekat.data.repositories

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update


@Dao
interface GameReviewDao {
    @Query("DELETE FROM gamereview WHERE online=:isOnline")
    suspend fun deleteOnline(isOnline: Boolean = true)
@Query("SELECT * FROM gamereview")
suspend fun getAll(): List<GameReview>
@Insert
suspend fun insertAll(vararg gamereviews: GameReview)
    @Query("DELETE FROM gamereview")
    suspend fun deleteAll()
    @Update
    suspend fun update(review: GameReview)

    @Query("SELECT * FROM gamereview WHERE online = :isOnline")
    suspend fun getOfflineReviews(isOnline: Boolean = false): List<GameReview>
}
