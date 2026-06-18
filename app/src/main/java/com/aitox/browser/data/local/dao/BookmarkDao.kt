package com.aitox.browser.data.local.dao
import androidx.room.*
import com.aitox.browser.data.local.entity.Bookmark
import kotlinx.coroutines.flow.Flow

@Dao
interface BookmarkDao {
    @Query("SELECT * FROM bookmarks ORDER BY timestamp DESC")
    fun getAllBookmarks(): Flow<List<Bookmark>>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBookmark(bookmark: Bookmark)
    @Delete
    suspend fun deleteBookmark(bookmark: Bookmark)
}
