package com.aitox.browser.core.webview
import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Environment
import android.webkit.URLUtil
import android.webkit.WebView
import android.widget.Toast
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class DownloadHelper @Inject constructor(@ApplicationContext private val context: Context) {
    fun setupDownloadListener(webView: WebView, onDownloadStart: (String, String, Long) -> Unit) {
        webView.setDownloadListener { url, userAgent, contentDisposition, mimetype, contentLength ->
            val request = DownloadManager.Request(Uri.parse(url)).apply {
                setMimeType(mimetype); addRequestHeader("User-Agent", userAgent); setDescription("Downloading file..."); setTitle(getFileName(contentDisposition, url)); setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED); setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, getFileName(contentDisposition, url))
            }
            val downloadManager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
            downloadManager.enqueue(request)
            onDownloadStart(getFileName(contentDisposition, url), mimetype, contentLength)
            Toast.makeText(context, "Download dimulai: ${getFileName(contentDisposition, url)}", Toast.LENGTH_SHORT).show()
        }
    }
    private fun getFileName(contentDisposition: String?, url: String): String { if (contentDisposition!= null) { val regex = Regex("filename=\"?([^\";]+)\"?"); val match = regex.find(contentDisposition); if (match!= null) return match.groupValues[1] }; return URLUtil.guessFileName(url, contentDisposition, null) }
}
