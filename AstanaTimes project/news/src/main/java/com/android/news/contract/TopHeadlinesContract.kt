package com.android.news.contract

import com.android.common.MvpPresenter
import com.android.common.MvpView
import com.android.news.model.Article


interface TopHeadlinesContract {

    interface View : MvpView {
        fun setupAdapter()
        fun show(articles: List<Article>)
        fun showProgressBar()
        fun hideProgressBar()
        fun showNewsDetails(article: Article)
        fun showMore(articles: List<Article>)
    }

    interface Presenter : MvpPresenter<View> {
        fun loadData(keyword: String, pageSize: Int)
        fun reloadData()
        fun incrementPage()
        fun loadMoreData(keyword: String, pageSize: Int)
    }
}