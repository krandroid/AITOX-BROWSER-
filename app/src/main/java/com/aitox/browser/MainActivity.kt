package com.aitox.browser
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.hilt.navigation.compose.hiltViewModel
import com.aitox.browser.core.webview.AitoxWebView
import com.aitox.browser.theme.AitoxTheme
import com.aitox.browser.ui.screens.BookmarkScreen
import com.aitox.browser.ui.screens.BrowserViewModel
import com.aitox.browser.ui.screens.HistoryScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AitoxTheme {
                var currentUrl by remember { mutableStateOf("https://google.com") }
                var showBookmarks by remember { mutableStateOf(false) }
                var showHistory by remember { mutableStateOf(false) }
                val viewModel: BrowserViewModel = hiltViewModel()
                
                Scaffold(
                    bottomBar = {
                        NavigationBar {
                            NavigationBarItem(selected = !showBookmarks && !showHistory, onClick = { showBookmarks = false; showHistory = false }, icon = { Text("Browser") }, label = { Text("Browser") })
                            NavigationBarItem(selected = showBookmarks, onClick = { showBookmarks = true; showHistory = false }, icon = { Text("⭐") }, label = { Text("Bookmark") })
                            NavigationBarItem(selected = showHistory, onClick = { showHistory = true; showBookmarks = false }, icon = { Text("🕒") }, label = { Text("History") })
                        }
                    }
                ) { padding ->
                    Box(Modifier.padding(padding)) {
                        when {
                            showBookmarks -> BookmarkScreen { url -> currentUrl = url; showBookmarks = false }
                            showHistory -> HistoryScreen()
                            else -> AitoxWebView(url = currentUrl)
                        }
                    }
                }
            }
        }
    }
}
