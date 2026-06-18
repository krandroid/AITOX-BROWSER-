package com.aitox.browser.ui.screens
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun HistoryScreen(viewModel: BrowserViewModel = hiltViewModel()) {
    val history by viewModel.history.collectAsState()
    
    LazyColumn(Modifier.fillMaxSize().padding(16.dp)) {
        item { 
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("History", style = MaterialTheme.typography.headlineMedium)
                TextButton(onClick = { viewModel.clearHistory() }) { Text("Clear") }
            }
        }
        items(history) { h ->
            Card(Modifier.fillMaxWidth().padding(4.dp)) {
                Column(Modifier.padding(12.dp)) {
                    Text(h.title, style = MaterialTheme.typography.titleMedium)
                    Text(h.url, style = MaterialTheme.typography.bodySmall)
                }
            }
        }
    }
}
