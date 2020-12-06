package fr.centrale.newsapiapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.*
import org.json.JSONArray

class MainActivity : AppCompatActivity() {

    val SOURCES_URL = "https://newsapi.org/v2/sources?apiKey=d31f5fa5f03443dd8a1b9e3fde92ec34&language=fr"
    var sources = JSONArray()
    val BASE_ARTICLES_URL = "https://newsapi.org/v2/everything?apiKey=d31f5fa5f03443dd8a1b9e3fde92ec34&language=fr"
    var articlesData = ArrayList<ArticlePreview>()

    lateinit var queue: RequestQueue
    lateinit var recyclerView: RecyclerView
    lateinit var viewManager: LinearLayoutManager
    lateinit var viewAdapter: CustomAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        queue = Volley.newRequestQueue(this)
        getSources()

        val testApiRequestButton = findViewById<Button>(R.id.testApiRequest)
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
                formatDataSet(response.getJSONArray("articles"))
                setUpRecyclerView()
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

    private fun formatDataSet(articles: JSONArray) {
        Log.d("TAG", articles.toString())
            for (index in 0 until articles.length()) {
                val article = articles.getJSONObject(index)
                val articlePreview = ArticlePreview(article.getString("title"), article.getString("author"), article.getString("publishedAt"), article.getString("urlToImage"))
                articlesData.add(articlePreview)
            }
    }

    private fun setUpRecyclerView() {
        viewManager = LinearLayoutManager(this)
        viewAdapter = CustomAdapter(articlesData)

        recyclerView = findViewById<RecyclerView>(R.id.recyclerView).apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }
    }
}