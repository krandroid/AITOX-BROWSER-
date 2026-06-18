package com.aitox.browser.data.local.dao
import androidx.room.*
import com.aitox.browser.data.local.entity.History
import kotlinx.coroutines.flow.Flow

@Dao
interface HistoryDao {
    @Query("SELECT * FROM history ORDER BY timestamp DESC LIMIT 100")
    fun getAllHistory(): Flow<List<History>>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHistory(history: History)
    @Query("DELETE FROM history")
    suspend fun clearHistory()
}
