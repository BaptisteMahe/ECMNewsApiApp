package fr.centrale.newsapiapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class CustomAdapter(private val dataSet: ArrayList<ArticlePreview>,
                    private val mOnArticleListener: OnArticleListener)
    : RecyclerView.Adapter<CustomAdapter.ViewHolder>()  {

    class ViewHolder(view: View, onArticleListener: OnArticleListener): RecyclerView.ViewHolder(view) {
        val txtTitle: TextView
        val txtAuthor: TextView
        val txtDate: TextView
        val imagePreview: ImageView

        init {
            view.setOnClickListener{
                onArticleListener.onArticleClick(adapterPosition)
            }
            txtTitle =  view.findViewById(R.id.txtTitle)
            txtAuthor = view.findViewById(R.id.txtAuthor)
            txtDate = view.findViewById(R.id.txtDate)
            imagePreview = view.findViewById(R.id.imagePreview)
        }
    }

    interface OnArticleListener{
        fun onArticleClick(position: Int)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.article_preview, viewGroup, false)
        return ViewHolder(view, mOnArticleListener)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.txtTitle.text = dataSet[position].title
        viewHolder.txtAuthor.text = dataSet[position].author
        viewHolder.txtDate.text = dataSet[position].date
        Picasso.get().load(dataSet[position].urlToImage).into(viewHolder.imagePreview)
    }

    override fun getItemCount() = dataSet.size
}