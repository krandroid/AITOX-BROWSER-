package com.aitox.browser.presentation.components
import android.view.View
import android.webkit.WebChromeClient
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.key
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.aitox.browser.core.manager.TabManager
import com.aitox.browser.core.manager.BrowserTab
import com.aitox.browser.core.webview.AdBlocker
import com.aitox.browser.core.webview.AitoxWebViewClient
import com.aitox.browser.core.webview.AitoxWebViewFactory
import com.aitox.browser.core.webview.AitoxWebChromeClient
import com.aitox.browser.core.webview.DownloadHelper
import com.aitox.browser.core.webview.LongPressHandler
import com.aitox.browser.core.webview.WebViewController
import com.aitox.browser.data.repository.BrowserRepository

@Composable fun WebViewContainer(tab: BrowserTab, tabManager: TabManager, webViewController: WebViewController, webViewFactory: AitoxWebViewFactory, repository: BrowserRepository, adBlocker: AdBlocker, downloadHelper: DownloadHelper, longPressHandler: LongPressHandler, onLinkLongPress: (com.aitox.browser.core.webview.LinkContextMenu) -> Unit, onSslError: (String) -> Unit, onFullscreenVideo: (View?, WebChromeClient.CustomViewCallback?) -> Unit) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    key(tab.id) {
        AndroidView(factory = { ctx ->
            val webView = webViewFactory.create(ctx, tab.isIncognito)
            webView.webViewClient = AitoxWebViewClient(tabId = tab.id, tabManager = tabManager, repository = repository, onSslError = onSslError, onError = { code, desc, url -> webView.loadData(ErrorPageGenerator.getErrorHtml(code, desc, url, true), "text/html", "UTF-8") }, adBlocker = adBlocker)
            webView.webChromeClient = AitoxWebChromeClient(tabId = tab.id, tabManager = tabManager, onShowCustomView = { view, callback -> onFullscreenVideo(view, callback) }, onHideCustomView = { onFullscreenVideo(null, null) }, onShowFileChooser = { callback, params -> true }, onPermissionRequest = { request -> request.grant(request.resources) })
            downloadHelper.setupDownloadListener(webView) { name, mime, size -> }
            longPressHandler.setupLongPress(webView, onLinkLongPress)
            webViewController.webView = webView
            if (tab.url.isNotEmpty()) webView.loadUrl(tab.url)
            webView
        }, update = { webView -> webViewController.webView = webView; if (tab.url.isNotEmpty() && webView.url!= tab.url) webView.loadUrl(tab.url) }, modifier = Modifier.fillMaxSize())
    }
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event -> when (event) { Lifecycle.Event.ON_PAUSE -> webViewController.webView?.onPause(); Lifecycle.Event.ON_RESUME -> webViewController.webView?.onResume(); else -> {} }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose { lifecycleOwner.lifecycle.removeObserver(observer) }
    }
}
