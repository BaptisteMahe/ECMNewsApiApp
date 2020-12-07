package fr.centrale.newsapiapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso

class ArticleActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_article)

        setUpTextViews()

        findViewById<Button>(R.id.linkButton).setOnClickListener{
            val webViewIntent = Intent(this, WebViewActivity::class.java)
            webViewIntent.putExtra("link", intent.getStringExtra("link"))
            startActivity(webViewIntent)
        }

        Picasso.get().load(intent.getStringExtra("urlToImage")).into(findViewById<ImageView>(R.id.imageView))
    }

    private fun setUpTextViews() {
        findViewById<TextView>(R.id.titleTextView).text = intent.getStringExtra("title")
        findViewById<TextView>(R.id.authorTextView).text = intent.getStringExtra("author")
        findViewById<TextView>(R.id.dateTextView).text = intent.getStringExtra("date")
        findViewById<TextView>(R.id.sourceTextView).text = intent.getStringExtra("sourceName")
        findViewById<TextView>(R.id.desciptionTextView).text = intent.getStringExtra("description")
    }
}