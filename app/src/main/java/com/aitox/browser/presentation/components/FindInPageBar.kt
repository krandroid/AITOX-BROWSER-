package com.aitox.browser.presentation.components
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.KeyboardArrowDown
import androidx.compose.material.icons.outlined.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aitox.browser.core.webview.WebViewController

@Composable fun FindInPageBar(webViewController: WebViewController, onClose: () -> Unit) {
    var query by remember { mutableStateOf("") }
    var matchCount by remember { mutableStateOf("") }
    Surface(modifier = Modifier.fillMaxWidth().padding(horizontal = 12.dp, vertical = 4.dp), shape = RoundedCornerShape(12.dp), color = MaterialTheme.colorScheme.surface, shadowElevation = 8.dp) {
        Row(Modifier.padding(horizontal = 12.dp, vertical = 8.dp), verticalAlignment = Alignment.CenterVertically) {
            TextField(value = query, onValueChange = { query = it; if (it.length >= 2) webViewController.findInPage(it) else webViewController.clearFindResults() }, modifier = Modifier.weight(1f).height(40.dp), placeholder = { Text("Cari di halaman...", fontSize = 14.sp) }, singleLine = true, colors = TextFieldDefaults.colors(focusedContainerColor = Color.Transparent, unfocusedContainerColor = Color.Transparent, focusedIndicatorColor = Color.Transparent, unfocusedIndicatorColor = Color.Transparent))
            Text(matchCount, fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.padding(horizontal = 8.dp))
            IconButton(onClick = { webViewController.findNext(false) }) { Icon(Icons.Outlined.KeyboardArrowUp, contentDescription = "Previous", tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(20.dp)) }
            IconButton(onClick = { webViewController.findNext(true) }) { Icon(Icons.Outlined.KeyboardArrowDown, contentDescription = "Next", tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(20.dp)) }
            IconButton(onClick = { onClose() }) { Icon(Icons.Outlined.Close, contentDescription = "Close", tint = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.size(20.dp)) }
        }
    }
}
