package fr.centrale.newsapiapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.webkit.WebView

class WebViewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view)
        val articleWebView: WebView = findViewById(R.id.articleWebView)
        Log.d("WEB_VIEW", intent.getStringExtra("link"))
        articleWebView.loadUrl(intent.getStringExtra("link"))
    }
}