package com.ankuranurag2.newsapp.ui.webview

import android.os.Bundle
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView

class WebViewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainContent(intent.getStringExtra(ARGS_URL_EXTRA) ?: "")
        }
    }

    companion object {
        const val ARGS_URL_EXTRA = "url"
    }
}

@Composable
fun MainContent(mUrl: String) {
    Scaffold(
        content = { WebViewContent(Modifier.padding(it), mUrl) }
    )
}

@Composable
fun WebViewContent(modifier: Modifier, mUrl: String) {
    AndroidView(
        modifier = modifier,
        factory = {
            WebView(it).apply {
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
                webViewClient = WebViewClient()
                loadUrl(mUrl)
            }
        },
        update = {
            it.loadUrl(mUrl)
        })
}