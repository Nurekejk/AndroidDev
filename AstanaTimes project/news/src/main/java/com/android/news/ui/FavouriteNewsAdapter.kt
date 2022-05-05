package com.android.news.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.news.R
import com.android.news.model.Article
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.favourites_item.view.*
import kotlinx.android.synthetic.main.item_list.view.*
import kotlinx.android.synthetic.main.item_list.view.newsDateTextView
import kotlinx.android.synthetic.main.item_list.view.newsImageTextView
import kotlinx.android.synthetic.main.item_list.view.newsItemContainer
import kotlinx.android.synthetic.main.item_list.view.newsTitleTextView


class FavouriteNewsAdapter(
    private val newsClickListener: (article: Article) -> Unit,
    private val deleteClickListener: (article: Article, position: Int) -> Unit
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val newsList = mutableListOf<Article>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return NewsViewHolder(inflater, parent)
    }

    override fun getItemCount(): Int = newsList.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as NewsViewHolder).bind(newsList[position], newsClickListener, deleteClickListener)
    }

    fun addItems(list: List<Article>) {
        newsList.clear()
        newsList.addAll(list)
        notifyDataSetChanged()
    }

    fun removeItem(position: Int){
        if(newsList.size >= position){
            newsList.removeAt(position)
            notifyDataSetChanged()
        }
    }

    private class NewsViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
        RecyclerView.ViewHolder(inflater.inflate(R.layout.favourites_item, parent, false)) {

        private val newsImage = itemView.newsImageTextView
        private val newsTitle = itemView.newsTitleTextView
        private val newsDate = itemView.newsDateTextView
        private val newsItem = itemView.newsItemContainer
        private val deleteItem = itemView.deleteImageView

        fun bind(item: Article, newsClickListener: (article: Article) -> Unit,
                 deleteClickListener: (article: Article, position: Int) -> Unit) {
            Picasso.get().load(item.urlToImage).into(newsImage)
            newsTitle.text = item.title
            newsDate.text = item.publishedAt
            newsItem.setOnClickListener { newsClickListener(item) }
            deleteItem.setOnClickListener { deleteClickListener(item, adapterPosition) }
        }
    }
}