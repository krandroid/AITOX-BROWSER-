package com.aitox.browser.presentation.components
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.ArrowForward
import androidx.compose.material.icons.outlined.Refresh
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Stop
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
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable fun AddressBar(url: String, title: String, isLoading: Boolean, canGoBack: Boolean, canGoForward: Boolean, isBookmarked: Boolean, onUrlSubmit: (String) -> Unit, onBack: () -> Unit, onForward: () -> Unit, onReload: () -> Unit, onStop: () -> Unit, onToggleBookmark: () -> Unit, onShowFind: () -> Unit) {
    var textFieldValue by remember { mutableStateOf(TextFieldValue(url)) }
    Surface(tonalElevation = 2.dp, color = MaterialTheme.colorScheme.surface, modifier = Modifier.fillMaxWidth()) {
        Row(Modifier.fillMaxWidth().height(56.dp).padding(horizontal = 8.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
            IconButton(onClick = onBack, enabled = canGoBack) { Icon(Icons.Outlined.ArrowBack, contentDescription = "Back", tint = if (canGoBack) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.38f), modifier = Modifier.size(20.dp)) }
            IconButton(onClick = onForward, enabled = canGoForward) { Icon(Icons.Outlined.ArrowForward, contentDescription = "Forward", tint = if (canGoForward) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.38f), modifier = Modifier.size(20.dp)) }
            Surface(Modifier.weight(1f).height(40.dp), shape = RoundedCornerShape(20.dp), color = MaterialTheme.colorScheme.surfaceVariant) {
                TextField(value = textFieldValue, onValueChange = { textFieldValue = it }, modifier = Modifier.fillMaxWidth(), placeholder = { Text("Cari atau masukkan alamat...", fontSize = 14.sp, color = MaterialTheme.colorScheme.onSurfaceVariant) }, singleLine = true, colors = TextFieldDefaults.colors(focusedContainerColor = Color.Transparent, unfocusedContainerColor = Color.Transparent, focusedIndicatorColor = Color.Transparent, unfocusedIndicatorColor = Color.Transparent), leadingIcon = { Icon(Icons.Outlined.Search, contentDescription = null, tint = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.size(18.dp)) }, keyboardOptions = androidx.compose.foundation.text.KeyboardOptions(imeAction = androidx.compose.ui.text.input.ImeAction.Go), keyboardActions = androidx.compose.foundation.text.KeyboardActions(onGo = { onUrlSubmit(textFieldValue.text); textFieldValue = TextFieldValue(textFieldValue.text) }))
            }
            BookmarkStarButton(currentUrl = url, isBookmarked = isBookmarked, onToggle = onToggleBookmark)
            IconButton(onClick = { if (isLoading) onStop() else onReload() }) { Icon(if (isLoading) Icons.Outlined.Stop else Icons.Outlined.Refresh, contentDescription = if (isLoading) "Stop" else "Reload", tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(20.dp)) }
        }
    }
}
