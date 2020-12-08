package fr.centrale.newsapiapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso


class CustomAdapter(private val dataSet: ArrayList<ArticlePreview>,
                    private val mOnArticleListener: OnArticleListener,
                    private val onBottomReachedListener: OnBottomReachedListener)
    : RecyclerView.Adapter<CustomAdapter.ViewHolder>()  {

    private val imgPlaceholder = Picasso.get().load("https://hlfppt.org/wp-content/uploads/2017/04/placeholder.png")
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

    interface OnBottomReachedListener {
        fun onBottomReached(position: Int)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
        return if (viewType == 0) {
            ViewHolder(view.inflate(R.layout.article_preview_img_left, viewGroup, false), mOnArticleListener)
        } else {
            ViewHolder(view.inflate(R.layout.article_preview_img_right, viewGroup, false), mOnArticleListener)
        }
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.txtTitle.text = dataSet[position].title
        viewHolder.txtAuthor.text = dataSet[position].author
        viewHolder.txtDate.text = dataSet[position].date

        if(dataSet[position].urlToImage == "null") {
            imgPlaceholder.into(viewHolder.imagePreview)
        } else {
            Picasso.get().load(dataSet[position].urlToImage).into(viewHolder.imagePreview)
        }

        if (position == (itemCount - 1)){
            onBottomReachedListener.onBottomReached(position);
        }
    }

    override fun getItemCount() = dataSet.size

    override fun getItemViewType(position: Int): Int {
        return position % 2
    }

    fun addArticles(newDataSet: ArrayList<ArticlePreview>) {
        for (article in newDataSet) {
            dataSet.add(article)
        }
    }
}