package com.aitox.browser.core.webview
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.viewinterop.AndroidView

@Composable
fun AitoxWebView(
    url: String,
    webViewRef: (WebView) -> Unit,
    onTitleReceived: (String) -> Unit,
    goBack: Boolean,
    goForward: Boolean,
    onNavigationDone: () -> Unit
) {
    AndroidView(
        factory = { context ->
            WebView(context).apply {
                webViewClient = object : WebViewClient() {
                    override fun onPageFinished(view: WebView?, url: String?) {
                        view?.title?.let { onTitleReceived(it) }
                    }
                }
                settings.javaScriptEnabled = true
                settings.domStorageEnabled = true
                loadUrl(url)
                webViewRef(this)
            }
        },
        update = { webView ->
            if (webView.url != url) webView.loadUrl(url)
            if (goBack) { webView.goBack(); onNavigationDone() }
            if (goForward) { webView.goForward(); onNavigationDone() }
        }
    )
}
