package com.android.news.contract

import com.android.common.MvpPresenter
import com.android.common.MvpView
import com.android.news.model.Article


interface NewsDetailsContract {

    interface View : MvpView {

    }

    interface Presenter : MvpPresenter<View> {
        fun saveArticle(article: Article)
    }
}