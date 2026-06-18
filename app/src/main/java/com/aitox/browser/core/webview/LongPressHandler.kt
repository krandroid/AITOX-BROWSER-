package com.aitox.browser.core.webview
import android.webkit.WebView
import javax.inject.Inject

class LongPressHandler @Inject constructor() {
    fun setupLongPress(webView: WebView, onLinkLongPress: (LinkContextMenu) -> Unit) {
        webView.setOnLongClickListener { view ->
            val result = view.hitTestResult
            when (result.type) {
                WebView.HitTestResult.SRC_ANCHOR_TYPE -> { val url = result.extra?: return@setOnLongClickListener false; onLinkLongPress(LinkContextMenu(url = url, type = LinkType.LINK)); true }
                WebView.HitTestResult.IMAGE_TYPE -> { val imageUrl = result.extra?: return@setOnLongClickListener false; onLinkLongPress(LinkContextMenu(url = imageUrl, type = LinkType.IMAGE)); true }
                WebView.HitTestResult.SRC_IMAGE_ANCHOR_TYPE -> { val url = result.extra?: return@setOnLongClickListener false; onLinkLongPress(LinkContextMenu(url = url, type = LinkType.IMAGE_LINK)); true }
                else -> false
            }
        }
    }
}
data class LinkContextMenu(val url: String, val title: String = "", val type: LinkType)
enum class LinkType { LINK, IMAGE, IMAGE_LINK }
