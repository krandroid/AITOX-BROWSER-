package com.aitox.browser.core.webview
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.net.http.SslError
import android.webkit.WebResourceRequest
import android.webkit.WebResourceResponse
import android.webkit.WebView
import androidx.webkit.WebViewClientCompat
import com.aitox.browser.core.manager.TabManager
import com.aitox.browser.data.repository.BrowserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AitoxWebViewClient(private val tabId: String, private val tabManager: TabManager, private val repository: BrowserRepository, private val onSslError: (String) -> Unit, private val onError: (String, String, String) -> Unit, private val adBlocker: AdBlocker) : WebViewClientCompat() {
    override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) { super.onPageStarted(view, url, favicon); url?.let { tabManager.updateTabState(tabId, isLoading = true, url = it) }; tabManager.updateTabNavigation(tabId, canGoBack = view?.canGoBack() == true, canGoForward = view?.canGoForward() == true) }
    override fun onPageFinished(view: WebView?, url: String?) { super.onPageFinished(view, url); view?.let { tabManager.updateTabState(tabId, isLoading = false, title = it.title?: "Untitled"); tabManager.updateTabNavigation(tabId, canGoBack = it.canGoBack(), canGoForward = it.canGoForward()); val tab = tabManager.getTab(tabId); if (tab!= null &&!tab.isIncognito && url!= null) CoroutineScope(Dispatchers.IO).launch { repository.insertHistory(url, it.title?: "Untitled") } }
    override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean { val url = request?.url?.toString()?: return false; if (!url.startsWith("http://") &&!url.startsWith("https://")) try { view?.context?.startActivity(Intent.ACTION_VIEW, Uri.parse(url))); return true } catch (e: Exception) { return true }; val downloadExtensions = listOf(".pdf", ".zip", ".apk", ".mp4", ".mp3", ".doc", ".docx", ".xls", ".xlsx", ".rar", ".7z"); if (downloadExtensions.any { url.lowercase().endsWith(it) }) return false; return false }
    override fun shouldInterceptRequest(view: WebView?, request: WebResourceRequest?): WebResourceResponse? { val url = request?.url?.toString()?: return null; if (adBlocker.isAd(url)) return adBlocker.getBlockedResponse(); return null }
    override fun onReceivedError(view: WebView?, request: WebResourceRequest?, error: android.webkit.WebResourceError?) { super.onReceivedError(view, request, error); if (request?.isForMainFrame == true) { val errorCode = error?.errorCode?.toString()?: "UNKNOWN"; val description = error?.description?.toString()?: "Unknown error"; val url = request.url.toString(); onError(errorCode, description, url) } }
    override fun onReceivedSslError(view: WebView?, handler: android.webkit.SslErrorHandler?, error: SslError?) { handler?.cancel(); error?.url?.let { onSslError(it) } }
    override fun onRenderProcessGone(view: WebView?, detail: android.webkit.RenderProcessGoneDetail?): Boolean { val lastUrl = view?.url; view?.destroy(); lastUrl?.let { view?.loadUrl(it) }; return true }
}
