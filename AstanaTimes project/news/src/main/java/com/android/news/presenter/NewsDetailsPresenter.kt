package com.android.news.presenter

import android.util.Log
import com.android.common.BasePresenter
import com.android.news.contract.EverythingContract
import com.android.news.contract.NewsDetailsContract
import com.android.news.contract.TopHeadlinesContract
import com.android.news.interactor.NewsInteractor
import com.android.news.model.Article
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class NewsDetailsPresenter (private val newsInteractor: NewsInteractor): BasePresenter<NewsDetailsContract.View>(),
    NewsDetailsContract.Presenter{


    override fun saveArticle(article: Article) {
        Observable.fromCallable {
            newsInteractor.saveItem(article)
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()
    }



}