package com.aitox.browser.presentation.components
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ContentCopy
import androidx.compose.material.icons.outlined.FileDownload
import androidx.compose.material.icons.outlined.Image
import androidx.compose.material.icons.outlined.ImageSearch
import androidx.compose.material.icons.outlined.Link
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material.icons.outlined.Tab
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aitox.browser.core.webview.LinkContextMenu
import com.aitox.browser.core.webview.LinkType

@OptIn(ExperimentalMaterial3Api::class)
@Composable fun LinkContextMenuSheet(contextMenu: LinkContextMenu?, onDismiss: () -> Unit, onAction: (String) -> Unit) {
    if (contextMenu == null) return
    ModalBottomSheet(onDismissRequest = onDismiss, containerColor = MaterialTheme.colorScheme.surface, shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)) {
        Column(Modifier.padding(vertical = 8.dp)) {
            Row(Modifier.padding(horizontal = 20.dp, vertical = 12.dp)) {
                Icon(Icons.Outlined.Link, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(20.dp).padding(end = 8.dp))
                Column { Text(contextMenu.url, fontSize = 13.sp, color = MaterialTheme.colorScheme.onSurface, maxLines = 2, overflow = TextOverflow.Ellipsis, fontFamily = FontFamily.Monospace); Text(contextMenu.type.name, fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant) }
            }
            HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
            MenuItemRow(Icons.Outlined.Tab, "Buka di tab baru") { onAction("open_new_tab") }
            MenuItemRow(Icons.Outlined.VisibilityOff, "Buka di tab samaran") { onAction("open_incognito") }
            MenuItemRow(Icons.Outlined.ContentCopy, "Salin link") { onAction("copy_link") }
            MenuItemRow(Icons.Outlined.Share, "Bagikan link") { onAction("share_link") }
            if (contextMenu.type == LinkType.IMAGE || contextMenu.type == LinkType.IMAGE_LINK) { HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant); MenuItemRow(Icons.Outlined.Image, "Buka gambar") { onAction("open_image") }; MenuItemRow(Icons.Outlined.FileDownload, "Download gambar") { onAction("download_image") }; MenuItemRow(Icons.Outlined.ImageSearch, "Cari gambar ini") { onAction("search_image") } }
            Spacer(Modifier.height(24.dp))
        }
    }
}
