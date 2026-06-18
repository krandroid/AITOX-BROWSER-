package com.aitox.browser.presentation.components
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable fun SslWarningDialog(url: String, onProceed: () -> Unit, onCancel: () -> Unit) {
    AlertDialog(onDismissRequest = onCancel, containerColor = MaterialTheme.colorScheme.surface, icon = { Icon(Icons.Outlined.Warning, contentDescription = null, tint = MaterialTheme.colorScheme.error, modifier = Modifier.size(32.dp)) }, title = { Text("Koneksi Tidak Aman", color = MaterialTheme.colorScheme.onSurface) }, text = {
        Column {
            Text("Situs ini memiliki sertifikat keamanan yang bermasalah.", color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 14.sp)
            Spacer(Modifier.height(8.dp))
            Text(url, color = MaterialTheme.colorScheme.primary, fontSize = 12.sp, fontFamily = FontFamily.Monospace)
            Spacer(Modifier.height(8.dp))
            Text("Data yang Anda kirim ke situs ini mungkin tidak aman.", color = MaterialTheme.colorScheme.error, fontSize = 13.sp)
        }
    }, confirmButton = { TextButton(onClick = onProceed) { Text("Tetap Lanjutkan", color = MaterialTheme.colorScheme.error) } }, dismissButton = { TextButton(onClick = onCancel) { Text("Kembali (Disarankan)", color = MaterialTheme.colorScheme.primary) } })
}
