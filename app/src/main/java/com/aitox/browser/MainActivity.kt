package com.aitox.browser
import android.os.Bundle
import android.webkit.WebView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.hilt.navigation.compose.hiltViewModel
import com.aitox.browser.core.webview.AitoxWebView
import com.aitox.browser.theme.AitoxTheme
import com.aitox.browser.ui.components.BrowserBar
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
                var urlInput by remember { mutableStateOf(currentUrl) }
                var webView by remember { mutableStateOf<WebView?>(null) }
                var canGoBack by remember { mutableStateOf(false) }
                var canGoForward by remember { mutableStateOf(false) }
                var triggerBack by remember { mutableStateOf(false) }
                var triggerForward by remember { mutableStateOf(false) }
                var pageTitle by remember { mutableStateOf("AITOX Browser") }
                var showBookmarks by remember { mutableStateOf(false) }
                var showHistory by remember { mutableStateOf(false) }
                val viewModel: BrowserViewModel = hiltViewModel()
                
                LaunchedEffect(webView) {
                    canGoBack = webView?.canGoBack() == true
                    canGoForward = webView?.canGoForward() == true
                }
                
                Scaffold(
                    topBar = {
                        BrowserBar(
                            url = urlInput,
                            onUrlChange = { urlInput = it },
                            onBack = { triggerBack = true },
                            onForward = { triggerForward = true },
                            onBookmark = { viewModel.addBookmark(currentUrl, pageTitle) },
                            canGoBack = canGoBack,
                            canGoForward = canGoForward
                        )
                    },
                    bottomBar = {
                        NavigationBar {
                            NavigationBarItem(selected = !showBookmarks && !showHistory, onClick = { showBookmarks = false; showHistory = false }, icon = { Text("🌐") }, label = { Text("Browser") })
                            NavigationBarItem(selected = showBookmarks, onClick = { showBookmarks = true; showHistory = false }, icon = { Text("⭐") }, label = { Text("Bookmark") })
                            NavigationBarItem(selected = showHistory, onClick = { showHistory = true; showBookmarks = false }, icon = { Text("🕒") }, label = { Text("History") })
                        }
                    }
                ) { padding ->
                    Box(Modifier.padding(padding)) {
                        when {
                            showBookmarks -> BookmarkScreen { url -> currentUrl = url; urlInput = url; showBookmarks = false }
                            showHistory -> HistoryScreen()
                            else -> {
                                Column {
                                    AitoxWebView(
                                        url = currentUrl,
                                        webViewRef = { webView = it },
                                        onTitleReceived = { pageTitle = it },
                                        goBack = triggerBack,
                                        goForward = triggerForward,
                                        onNavigationDone = { triggerBack = false; triggerForward = false; canGoBack = webView?.canGoBack() == true; canGoForward = webView?.canGoForward() == true }
                                    )
                                }
                                LaunchedEffect(urlInput) {
                                    if (urlInput != currentUrl && urlInput.startsWith("http")) {
                                        currentUrl = urlInput
                                        viewModel.addHistory(currentUrl, pageTitle)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
