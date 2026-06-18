package com.aitox.browser.core.webview
import android.net.Uri
import android.webkit.WebResourceResponse
import java.io.ByteArrayInputStream
import javax.inject.Inject

class AdBlocker @Inject constructor() {
    private val adDomains = hashSetOf("doubleclick.net","googlesyndication.com","googleadservices.com","google-analytics.com","googletagmanager.com","googletagservices.com","facebook.net","fbcdn.net","amazon-adsystem.com","adnxs.com","adsrvr.org","advertising.com","scorecardresearch.com","quantserve.com","chartbeat.net","taboola.com","outbrain.com","criteo.com","moatads.com","rubiconproject.com","pubmatic.com","openx.net","casalemedia.com","contextweb.com","yieldmanager.com","serving-sys.com","eyeviewdigital.com","turn.com","mathtag.com","bidswitch.net","adsymptotic.com","bluekai.com","exelator.com","demdex.net","agkn.com","rlcdn.com","crwdcntrl.net","simpli.fi","spotxchange.com","adform.net","smartadserver.com","teads.tv","hotjar.com","mixpanel.com","segment.io","amplitude.com","optimizely.com","fullstory.com","mouseflow.com","luckyorange.com","crazyegg.com")
    fun isAd(url: String): Boolean { val host = Uri.parse(url).host?.lowercase()?: return false; return adDomains.any { domain -> host.endsWith(domain) } }
    fun getBlockedResponse(): WebResourceResponse { return WebResourceResponse("text/plain", "UTF-8", ByteArrayInputStream("".toByteArray())) }
}
