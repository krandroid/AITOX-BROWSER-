package com.aitox.browser.core.manager

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

data class Tab(
    val id: String,
    var url: String,
    var title: String = "New Tab"
)

class TabManager {
    private val _tabs = MutableStateFlow(listOf(Tab("1", "https://google.com")))
    val tabs: StateFlow<List<Tab>> = _tabs
    
    private val _currentTabId = MutableStateFlow("1")
    val currentTabId: StateFlow<String> = _currentTabId
    
    fun addTab(url: String = "https://google.com") {
        val newTab = Tab(System.currentTimeMillis().toString(), url)
        _tabs.value = _tabs.value + newTab
        _currentTabId.value = newTab.id
    }
    
    fun closeTab(id: String) {
        _tabs.value = _tabs.value.filter { it.id != id }
    }
    
    fun switchTab(id: String) {
        _currentTabId.value = id
    }
}
