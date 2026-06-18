package com.aitox.browser.ui.screens
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun BookmarkScreen(viewModel: BrowserViewModel = hiltViewModel(), onBookmarkClick: (String) -> Unit) {
    val bookmarks by viewModel.bookmarks.collectAsState()
    
    LazyColumn(Modifier.fillMaxSize().padding(16.dp)) {
        item { Text("Bookmarks", style = MaterialTheme.typography.headlineMedium) }
        items(bookmarks) { bookmark ->
            Card(Modifier.fillMaxWidth().padding(4.dp).clickable { onBookmarkClick(bookmark.url) }) {
                Column(Modifier.padding(12.dp)) {
                    Text(bookmark.title, style = MaterialTheme.typography.titleMedium)
                    Text(bookmark.url, style = MaterialTheme.typography.bodySmall)
                }
            }
        }
    }
}
