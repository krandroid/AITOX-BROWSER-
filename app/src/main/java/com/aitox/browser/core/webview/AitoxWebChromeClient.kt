package com.aitox.browser.core.webview
import android.graphics.Bitmap
import android.net.Uri
import android.view.View
import android.webkit.GeolocationPermissions
import android.webkit.PermissionRequest
import android.webkit.ValueCallback
import android.webkit.WebChromeClient
import android.webkit.WebView
import com.aitox.browser.core.manager.TabManager

class AitoxWebChromeClient(private val tabId: String, private val tabManager: TabManager, private val onShowCustomView: (View, WebChromeClient.CustomViewCallback) -> Unit, private val onHideCustomView: () -> Unit, private val onShowFileChooser: (ValueCallback<Array<Uri>>, WebChromeClient.FileChooserParams) -> Boolean, private val onPermissionRequest: (PermissionRequest) -> Unit) : WebChromeClient() {
    override fun onProgressChanged(view: WebView?, newProgress: Int) { super.onProgressChanged(view, newProgress); tabManager.updateTabProgress(tabId, newProgress) }
    override fun onReceivedTitle(view: WebView?, title: String?) { super.onReceivedTitle(view, title); tabManager.updateTabState(tabId, title = title?: "Untitled") }
    override fun onReceivedIcon(view: WebView?, icon: Bitmap?) { super.onReceivedIcon(view, icon); icon?.let { tabManager.updateTabFavicon(tabId, it) } }
    override fun onShowCustomView(view: View?, callback: CustomViewCallback?) { super.onShowCustomView(view, callback); if (view!= null && callback!= null) onShowCustomView(view, callback) }
    override fun onHideCustomView() { super.onHideCustomView(); onHideCustomView() }
    override fun onShowFileChooser(webView: WebView?, filePathCallback: ValueCallback<Array<Uri>>?, fileChooserParams: FileChooserParams?): Boolean { return if (filePathCallback!= null && fileChooserParams!= null) onShowFileChooser(filePathCallback, fileChooserParams) else false }
    override fun onPermissionRequest(request: PermissionRequest?) { super.onPermissionRequest(request); request?.let { onPermissionRequest(it) } }
    override fun onCreateWindow(view: WebView?, isDialog: Boolean, isUserGesture: Boolean, resultMsg: android.os.Message?): Boolean { tabManager.addTab("", isIncognito = tabManager.getTab(tabId)?.isIncognito == true); val transport = resultMsg?.obj as WebView.WebViewTransport; val newWebView = WebView(view!!.context); transport.webView = newWebView; resultMsg.sendToTarget(); return true }
    override fun onGeolocationPermissionsShowPrompt(origin: String?, callback: GeolocationPermissions.Callback?) { callback?.invoke(origin, true, false) }
}
