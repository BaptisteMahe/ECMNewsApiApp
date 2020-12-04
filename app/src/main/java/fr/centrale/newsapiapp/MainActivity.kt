package fr.centrale.newsapiapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.*
import org.json.JSONArray

class MainActivity : AppCompatActivity() {

    val SOURCES_URL = "https://newsapi.org/v2/sources?apiKey=d31f5fa5f03443dd8a1b9e3fde92ec34&language=fr"
    var sources = JSONArray()
    val BASE_ARTICLES_URL = "https://newsapi.org/v2/everything?apiKey=d31f5fa5f03443dd8a1b9e3fde92ec34&language=fr"
    var articles = JSONArray()

    lateinit var queue: RequestQueue

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        queue = Volley.newRequestQueue(this)

        val testApiRequestButton = findViewById<Button>(R.id.testApiRequest)

        getSources()

        testApiRequestButton.setOnClickListener{
            getArticles(sources.getJSONObject(0).getString("id"), 1)
        }

    }

    private fun getSources() {
        val sourcesRequest = object: JsonObjectRequest(
            Request.Method.GET, SOURCES_URL, null,
            { response ->
                sources = response.getJSONArray("sources")
                Log.d("RECEIVED_SOURCES", sources.toString())
            },
            { error ->
                Log.d("TAG", "Something went wrong: $error") })
        {
            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers["User-Agent"] = "Mozilla/5.0"
                return headers
            }
        }

        queue.add(sourcesRequest)
    }

    private fun getArticles(sourceId: String, page: Number) {
        val url = "$BASE_ARTICLES_URL&sources=$sourceId&page=$page"
        val articlesRequest = object: JsonObjectRequest(
            Request.Method.GET, url + sourceId, null,
            { response ->
                articles = response.getJSONArray("articles")
                Log.d("RECEIVED_ARTICLES", articles.toString())
            },
            { error ->
                Log.d("TAG", "Something went wrong: $error") })
        {
            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers["User-Agent"] = "Mozilla/5.0"
                return headers
            }
        }

        queue.add(articlesRequest)
    }
}