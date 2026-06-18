package com.aitox.browser.presentation.screens.home
import android.webkit.WebChromeClient
import android.view.View
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.aitox.browser.core.manager.TabManager
import com.aitox.browser.core.webview.AdBlocker
import com.aitox.browser.core.webview.AitoxWebViewFactory
import com.aitox.browser.core.webview.DownloadHelper
import com.aitox.browser.core.webview.LongPressHandler
import com.aitox.browser.core.webview.WebViewController
import com.aitox.browser.data.repository.BrowserRepository
import com.aitox.browser.presentation.components.AddressBar
import com.aitox.browser.presentation.components.BrowserProgressBar
import com.aitox.browser.presentation.components.FindInPageBar
import com.aitox.browser.presentation.components.FullscreenVideoOverlay
import com.aitox.browser.presentation.components.LinkContextMenuSheet
import com.aitox.browser.presentation.components.SslWarningDialog
import com.aitox.browser.presentation.components.WebViewContainer

@OptIn(ExperimentalMaterial3Api::class)
@Composable fun HomeScreen(tabManager: TabManager, webViewFactory: AitoxWebViewFactory, repository: BrowserRepository, adBlocker: AdBlocker, downloadHelper: DownloadHelper, longPressHandler: LongPressHandler, viewModel: HomeViewModel = hiltViewModel()) {
    val tabs by viewModel.tabs.collectAsState()
    val activeTabId by viewModel.activeTabId.collectAsState()
    val activeTab = tabs.find { it.id == activeTabId }
    var showFindBar by remember { mutableStateOf(false) }
    var contextMenu by remember { mutableStateOf<com.aitox.browser.core.webview.LinkContextMenu?>(null) }
    var sslWarningUrl by remember { mutableStateOf<String?>(null) }
    var fullscreenVideo by remember { mutableStateOf<Pair<View?, WebChromeClient.CustomViewCallback?>?>(null) }
    val webViewController = remember { WebViewController() }
    Box(Modifier.fillMaxSize().statusBarsPadding()) {
        Column(Modifier.fillMaxSize()) {
            if (activeTab!= null) {
                AddressBar(url = activeTab.url, title = activeTab.title, isLoading = activeTab.isLoading, canGoBack = activeTab.canGoBack, canGoForward = activeTab.canGoForward, isBookmarked = viewModel.isBookmarked(activeTab.url), onUrlSubmit = { viewModel.navigate(it) }, onBack = { viewModel.goBack() }, onForward = { viewModel.goForward() }, onReload = { viewModel.reload() }, onStop = { viewModel.stopLoading() }, onToggleBookmark = { viewModel.toggleBookmark(activeTab.url, activeTab.title) }, onShowFind = { showFindBar = true })
                BrowserProgressBar(progress = activeTab.progress, isLoading = activeTab.isLoading)
                WebViewContainer(tab = activeTab, tabManager = tabManager, webViewController = webViewController, webViewFactory = webViewFactory, repository = repository, adBlocker = adBlocker, downloadHelper = downloadHelper, longPressHandler = longPressHandler, onLinkLongPress = { contextMenu = it }, onSslError = { sslWarningUrl = it }, onFullscreenVideo = { view, callback -> fullscreenVideo = Pair(view, callback) })
            }
        }
        if (showFindBar && activeTab!= null) FindInPageBar(webViewController) { showFindBar = false }
        if (contextMenu!= null) LinkContextMenuSheet(contextMenu) { contextMenu = null } { action -> when(action) { "open_new_tab" -> viewModel.createNewTab(contextMenu!!.url); "copy_link" -> {} }; contextMenu = null }
        if (sslWarningUrl!= null) SslWarningDialog(sslWarningUrl!!, onProceed = { sslWarningUrl = null }, onCancel = { sslWarningUrl = null })
        fullscreenVideo?.let { FullscreenVideoOverlay(it.first, it.second) { fullscreenVideo = null }
    }
}
