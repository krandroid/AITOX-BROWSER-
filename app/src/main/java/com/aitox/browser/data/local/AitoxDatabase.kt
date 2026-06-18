package com.aitox.browser.data.local
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.aitox.browser.data.local.dao.BookmarkDao
import com.aitox.browser.data.local.dao.HistoryDao
import com.aitox.browser.data.local.entity.Bookmark
import com.aitox.browser.data.local.entity.History

@Database(entities = [Bookmark::class, History::class], version = 1)
abstract class AitoxDatabase : RoomDatabase() {
    abstract fun bookmarkDao(): BookmarkDao
    abstract fun historyDao(): HistoryDao
    companion object {
        @Volatile private var INSTANCE: AitoxDatabase? = null
        fun getDatabase(context: Context): AitoxDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(context, AitoxDatabase::class.java, "aitox_db").build()
            }
        }
    }
}
