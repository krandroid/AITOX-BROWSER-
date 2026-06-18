package com.aitox.browser.data.local.entity
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bookmarks")
data class Bookmark(@PrimaryKey(autoGenerate = true) val id: Int = 0, val url: String, val title: String, val timestamp: Long = System.currentTimeMillis())
