package com.aitox.browser.presentation.components
import android.view.View
import android.webkit.WebChromeClient
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.zIndex

@Composable fun FullscreenVideoOverlay(customView: View?, callback: WebChromeClient.CustomViewCallback?, onDismiss: () -> Unit) {
    if (customView == null) return
    Box(Modifier.fillMaxSize().background(Color.Black).zIndex(100f)) {
        AndroidView(factory = { customView }, modifier = Modifier.fillMaxSize())
        IconButton(onClick = { callback?.onCustomViewHidden(); onDismiss() }, modifier = Modifier.align(Alignment.TopStart).statusBarsPadding().padding(8.dp)) {
            Icon(Icons.Outlined.Close, contentDescription = "Exit fullscreen", tint = Color.White, modifier = Modifier.size(28.dp))
        }
    }
}
