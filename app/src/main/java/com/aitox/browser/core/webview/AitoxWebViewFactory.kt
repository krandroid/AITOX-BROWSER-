package com.aitox.browser.core.webview
import android.content.Context
import android.view.ContextThemeWrapper
import android.webkit.CookieManager
import android.webkit.WebSettings
import android.webkit.WebView
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class AitoxWebViewFactory @Inject constructor(@ApplicationContext private val context: Context) {
    fun create(context: Context, isIncognito: Boolean = false): WebView {
        val webViewContext = ContextThemeWrapper(context, android.R.style.Theme_Material_Light_NoActionBar_Fullscreen)
        val webView = WebView(webViewContext)
        webView.settings.apply {
            javaScriptEnabled = true
            domStorageEnabled = true
            databaseEnabled = true
            useWideViewPort = true
            loadWithOverviewMode = true
            setSupportZoom(false)
            builtInZoomControls = false
            displayZoomControls = false
            mediaPlaybackRequiresUserGesture = false
            allowContentAccess = true
            allowFileAccess = true
            setSupportMultipleWindows(true)
            javaScriptCanOpenWindowsAutomatically = true
            mixedContentMode = WebSettings.MIXED_CONTENT_COMPATIBILITY_MODE
            cacheMode = WebSettings.LOAD_DEFAULT
            safeBrowsingEnabled = true
            val defaultUA = userAgentString
            val mobileUA = defaultUA.replace("X11; Linux x86_64", "").replace("Windows NT 10.0; Win64; x64", "").replace("Macintosh; Intel Mac OS X", "").let { if (!it.contains("Mobile")) "$it Mobile" else it }
            userAgentString = mobileUA
            if (isIncognito) cacheMode = WebSettings.LOAD_NO_CACHE
        }
        CookieManager.getInstance().apply { setAcceptCookie(true); setAcceptThirdPartyCookies(webView, false) }
        return webView
    }
}
