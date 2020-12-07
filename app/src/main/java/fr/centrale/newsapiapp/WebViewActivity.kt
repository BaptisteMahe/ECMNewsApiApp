package fr.centrale.newsapiapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ProgressBar
import androidx.core.view.isVisible
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley

class WebViewActivity : AppCompatActivity() {

    lateinit var articleWebView: WebView
    lateinit var loadingBar: ProgressBar
    lateinit var queue: RequestQueue

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view)
        queue = Volley.newRequestQueue(this)
        articleWebView = findViewById(R.id.articleWebView)
        loadingBar = findViewById(R.id.loadingBar)
        loadingBar.isVisible = true

        articleWebView.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView, weburl: String) {
                loadingBar.isVisible = false
            }
        }

        articleWebView.loadUrl(intent.getStringExtra("link"))
    }
}
