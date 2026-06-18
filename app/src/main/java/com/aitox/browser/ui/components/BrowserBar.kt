package com.aitox.browser.ui.components
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun BrowserBar(
    url: String,
    onUrlChange: (String) -> Unit,
    onBack: () -> Unit,
    onForward: () -> Unit,
    onBookmark: () -> Unit,
    canGoBack: Boolean,
    canGoForward: Boolean
) {
    Column {
        Row(Modifier.fillMaxWidth().padding(8.dp), horizontalArrangement = Arrangement.spacedBy(4.dp)) {
            IconButton(onClick = onBack, enabled = canGoBack) { Text("◀") }
            IconButton(onClick = onForward, enabled = canGoForward) { Text("▶") }
            IconButton(onClick = onBookmark) { Text("⭐") }
        }
        OutlinedTextField(
            value = url,
            onValueChange = onUrlChange,
            modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp),
            placeholder = { Text("Search or enter URL") },
            singleLine = true
        )
    }
}
