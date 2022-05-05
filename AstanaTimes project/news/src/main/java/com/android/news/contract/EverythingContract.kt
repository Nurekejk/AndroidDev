package com.android.news.contract

import com.android.common.MvpPresenter
import com.android.common.MvpView
import com.android.news.model.Article


interface EverythingContract {

    interface View : MvpView {
        fun setupAdapter()
        fun show(articles: List<Article>)
        fun showNewsDetails(article: Article)
        fun showMore(articles: List<Article>)

    }

    interface Presenter : MvpPresenter<View> {
        fun loadData(keyword: String, pageSize: Int)
        fun incrementPage()
        fun loadMoreData(keyword: String, pageSize: Int)
    }
}