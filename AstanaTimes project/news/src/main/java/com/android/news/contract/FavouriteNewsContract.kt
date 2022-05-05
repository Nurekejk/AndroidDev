package com.android.news.contract

import com.android.common.MvpPresenter
import com.android.common.MvpView
import com.android.news.model.Article


interface FavouriteNewsContract {

    interface View : MvpView {
        fun show(articles: List<Article>)
        fun showNewsDetails(article: Article)

    }

    interface Presenter : MvpPresenter<View> {
        fun loadData()
        fun deleteItem(article: Article)
    }
}