package com.aitox.browser.core.webview
import android.webkit.WebView
import javax.inject.Inject

class WebViewController @Inject constructor() {
    var webView: WebView? = null
    fun goBack() { webView?.goBack() }
    fun goForward() { webView?.goForward() }
    fun reload() { webView?.reload() }
    fun stopLoading() { webView?.stopLoading() }
    fun loadUrl(url: String) { webView?.loadUrl(url) }
    fun findInPage(query: String) { webView?.findAllAsync(query) }
    fun findNext(forward: Boolean) { webView?.findNext(forward) }
    fun clearFindResults() { webView?.clearMatches() }
}
