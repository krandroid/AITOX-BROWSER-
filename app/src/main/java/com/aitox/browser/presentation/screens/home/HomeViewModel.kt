package com.aitox.browser.presentation.screens.home
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aitox.browser.core.manager.BrowserTab
import com.aitox.browser.core.manager.TabManager
import com.aitox.browser.data.repository.BrowserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel class HomeViewModel @Inject constructor(private val tabManager: TabManager, private val repository: BrowserRepository) : ViewModel() {
    private val _tabs = MutableStateFlow<List<BrowserTab>>(emptyList())
    val tabs: StateFlow<List<BrowserTab>> = _tabs.asStateFlow()
    private val _activeTabId = MutableStateFlow("")
    val activeTabId: StateFlow<String> = _activeTabId.asStateFlow()
    private val _bookmarks = MutableStateFlow<List<com.aitox.browser.data.entity.BookmarkEntity>>(emptyList())
    val bookmarks: StateFlow<List<com.aitox.browser.data.entity.BookmarkEntity>> = _bookmarks.asStateFlow()
    init { tabManager.tabs.collectIn(viewModelScope) { _tabs.value = it }; tabManager.activeTabId.collectIn(viewModelScope) { _activeTabId.value = it }; loadBookmarks() }
    fun createNewTab(url: String = "", isIncognito: Boolean = false) { tabManager.addTab(url, isIncognito) }
    fun closeTab(tabId: String) { tabManager.closeTab(tabId) }
    fun switchTab(tabId: String) { tabManager.switchTab(tabId) }
    fun navigate(url: String) { val activeId = _activeTabId.value; if (activeId.isNotEmpty()) tabManager.updateTabState(activeId, url = url) }
    fun reload() { val activeId = _activeTabId.value; if (activeId.isNotEmpty()) tabManager.getTab(activeId)?.webView?.reload() }
    fun goBack() { val activeId = _activeTabId.value; tabManager.getTab(activeId)?.webView?.goBack() }
    fun goForward() { val activeId = _activeTabId.value; tabManager.getTab(activeId)?.webView?.goForward() }
    fun stopLoading() { val activeId = _activeTabId.value; tabManager.getTab(activeId)?.webView?.stopLoading() }
    fun toggleBookmark(url: String, title: String) { viewModelScope.launch { if (repository.isBookmarked(url)) repository.deleteBookmark(url) else repository.insertBookmark(com.aitox.browser.data.entity.BookmarkEntity(url = url, title = title)); loadBookmarks() }
    fun isBookmarked(url: String): Boolean = _bookmarks.value.any { it.url == url }
    private fun loadBookmarks() { viewModelScope.launch { _bookmarks.value = repository.getAllBookmarks() }
}
