package fr.centrale.newsapiapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import com.android.volley.Request
import com.android.volley.toolbox.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val testApiRequestButton = findViewById<Button>(R.id.testApiRequest)

        testApiRequestButton.setOnClickListener{
            val queue = Volley.newRequestQueue(this)
            val url = "https://newsapi.org/v2/sources?apiKey=d31f5fa5f03443dd8a1b9e3fde92ec34&language=fr"

            val stringRequest = object: JsonObjectRequest(
                Request.Method.GET, url, null,
                { response ->
                    Log.d("TAG", response.toString())
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

            queue.add(stringRequest)
        }

    }
}