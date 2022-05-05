package com.android.news.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.news.R
import com.android.news.model.Article
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_list.view.*


class NewsAdapter(private val newsClickListener: (article: Article) -> Unit): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val newsList = mutableListOf<Article>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return NewsViewHolder(inflater, parent)
    }

    override fun getItemCount(): Int = newsList.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as NewsViewHolder).bind(newsList[position], newsClickListener)
    }

    fun addItems(list: List<Article>) {
        newsList.clear()
        newsList.addAll(list)
        notifyDataSetChanged()
    }

    fun appendData(list: List<Article>){
        list.forEach {
            newsList.add(it)
        }
        notifyDataSetChanged()
    }

    private class NewsViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
        RecyclerView.ViewHolder(inflater.inflate(R.layout.item_list, parent, false)) {

        private val newsImage = itemView.newsImageTextView
        private val newsTitle = itemView.newsTitleTextView
        private val newsDate = itemView.newsDateTextView
        private val newsItem = itemView.newsItemContainer

        fun bind(item: Article, newsClickListener: (article: Article) -> Unit) {
            Picasso.get().load(item.urlToImage).into(newsImage)
            newsTitle.text = item.title
            newsDate.text = item.publishedAt
            newsItem.setOnClickListener { newsClickListener(item) }
        }
    }
}