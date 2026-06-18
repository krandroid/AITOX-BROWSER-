package com.aitox.browser.ui.screens
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aitox.browser.data.repository.BrowserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BrowserViewModel @Inject constructor(private val repo: BrowserRepository) : ViewModel() {
    val bookmarks: StateFlow<List<com.aitox.browser.data.local.entity.Bookmark>> = repo.getBookmarks().stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    val history: StateFlow<List<com.aitox.browser.data.local.entity.History>> = repo.getHistory().stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    
    fun addBookmark(url: String, title: String) = viewModelScope.launch { repo.addBookmark(url, title) }
    fun deleteBookmark(b: com.aitox.browser.data.local.entity.Bookmark) = viewModelScope.launch { repo.deleteBookmark(b) }
    fun addHistory(url: String, title: String) = viewModelScope.launch { repo.addHistory(url, title) }
    fun clearHistory() = viewModelScope.launch { repo.clearHistory() }
}
