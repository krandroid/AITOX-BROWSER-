package com.aitox.browser.data.repository
import com.aitox.browser.data.local.dao.BookmarkDao
import com.aitox.browser.data.local.dao.HistoryDao
import com.aitox.browser.data.local.entity.Bookmark
import com.aitox.browser.data.local.entity.History
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BrowserRepository @Inject constructor(
    private val bookmarkDao: BookmarkDao,
    private val historyDao: HistoryDao
) {
    fun getBookmarks() = bookmarkDao.getAllBookmarks()
    fun getHistory() = historyDao.getAllHistory()
    suspend fun addBookmark(url: String, title: String) = bookmarkDao.insertBookmark(Bookmark(url = url, title = title))
    suspend fun deleteBookmark(bookmark: Bookmark) = bookmarkDao.deleteBookmark(bookmark)
    suspend fun addHistory(url: String, title: String) = historyDao.insertHistory(History(url = url, title = title))
    suspend fun clearHistory() = historyDao.clearHistory()
}
