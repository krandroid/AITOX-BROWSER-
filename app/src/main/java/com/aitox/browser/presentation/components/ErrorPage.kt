package com.aitox.browser.presentation.components
object ErrorPageGenerator {
    fun getErrorHtml(errorCode: String, description: String, url: String, isDark: Boolean): String {
        val bg = if (isDark) "#060912" else "#F4F7F6"
        val text = if (isDark) "#FFFFFF" else "#1C2B2A"
        val subtext = if (isDark) "#8CFFFFFF" else "#8C1C2B2A"
        val accent = if (isDark) "#4FC3F7" else "#00897B"
        return """<!DOCTYPE html><html><head><meta name="viewport" content="width=device-width, initial-scale=1.0"><style>body { font-family: -apple-system, sans-serif; background: $bg; color: $text; display: flex; align-items: center; justify-content: center; min-height: 100vh; margin: 0; padding: 20px; text-align: center; }.container { max-width: 320px; }.icon { font-size: 48px; margin-bottom: 16px; }h1 { font-size: 18px; font-weight: 600; margin-bottom: 8px; }p { font-size: 14px; color: $subtext; line-height: 1.5; margin-bottom: 24px; }.code { font-size: 12px; color: $subtext; opacity: 0.6; }button { background: transparent; border: 1.5px solid $accent; color: $accent; padding: 10px 28px; border-radius: 24px; font-size: 14px; font-weight: 500; cursor: pointer; margin-top: 16px; }</style></head><body><div class="container"><div class="icon">⚠️</div><h1>Tidak dapat memuat halaman</h1><p>$description</p><div class="code">Error: $errorCode</div><button onclick="window.location.reload()">Coba Lagi</button></div></body></html>""".trimIndent()
    }
}
