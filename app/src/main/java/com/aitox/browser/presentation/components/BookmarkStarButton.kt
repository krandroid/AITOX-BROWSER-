package com.aitox.browser.presentation.components
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable fun BookmarkStarButton(currentUrl: String, isBookmarked: Boolean, onToggle: () -> Unit) {
    IconButton(onClick = onToggle) {
        Icon(imageVector = if (isBookmarked) Icons.Filled.Bookmark else Icons.Outlined.BookmarkBorder, contentDescription = "Bookmark", tint = if (isBookmarked) Color(0xFFFFD700) else MaterialTheme.colorScheme.primary, modifier = Modifier.size(20.dp))
    }
}
