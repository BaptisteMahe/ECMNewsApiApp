package fr.centrale.newsapiapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.*
import org.json.JSONArray

class MainActivity : AppCompatActivity(), CustomAdapter.OnArticleListener {

    val SOURCES_URL = "https://newsapi.org/v2/sources?apiKey=d31f5fa5f03443dd8a1b9e3fde92ec34&language=fr"
    var sources = JSONArray()
    var currentSourceId = ""
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
        getSources(savedInstanceState?.getString("sourceId"))
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.menu_layout, menu)
        return true
    }

    override fun onSaveInstanceState(savedInstanceState: Bundle) {
        savedInstanceState.putString("sourceId", currentSourceId)
        super.onSaveInstanceState(savedInstanceState)
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        super.onPrepareOptionsMenu(menu)
        if (sources.length() > 0) {
            menu?.clear()
        }
        for (index in 0 until sources.length()) {
            menu?.add(0, index, index, sources.getJSONObject(index).getString("name"))
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        super.onOptionsItemSelected(item)
        currentSourceId = sources.getJSONObject(item.itemId).getString("id")
        getArticles(currentSourceId, 1)
        return true
    }

    override fun onArticleClick(position: Int) {
        val article = articlesData[position]
        val monIntent = Intent(this, ArticleActivity::class.java)
        monIntent.putExtra("title", article.title)
        monIntent.putExtra("author", article.author)
        monIntent.putExtra("date", article.date)
        monIntent.putExtra("sourceName", article.sourceName)
        monIntent.putExtra("description", article.description)
        monIntent.putExtra("link", article.link)
        monIntent.putExtra("urlToImage", article.urlToImage)
        startActivity(monIntent)
    }

    private fun getSources(savedSourceId: String?) {
        val sourcesRequest = object: JsonObjectRequest(
            Request.Method.GET, SOURCES_URL, null,
            { response ->
                sources = response.getJSONArray("sources")
                if(savedSourceId != null) {
                    Log.d("SOURCEID", savedSourceId)
                    getArticles(savedSourceId, 1)
                } else {
                    getArticles(sources.getJSONObject(0).getString("id"), 1)
                }
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
                Log.d("ARTICLES", response.getJSONArray("articles").toString())
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
        articlesData = ArrayList()
            for (index in 0 until articles.length()) {
                val article = articles.getJSONObject(index)
                Log.d("ARTICLE", article.toString())
                val articlePreview = ArticlePreview(article.getString("title"), article.getString("author"), article.getString("publishedAt"), article.getJSONObject("source").getString("name"), article.getString("description"), article.getString("url"), article.getString("urlToImage"))
                articlesData.add(articlePreview)
            }
    }

    private fun setUpRecyclerView() {
        viewManager = LinearLayoutManager(this)
        viewAdapter = CustomAdapter(articlesData, this)

        recyclerView = findViewById<RecyclerView>(R.id.recyclerView).apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }
        recyclerView
    }
}