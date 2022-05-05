package com.android.news.presenter

import android.util.Log
import com.android.common.BasePresenter
import com.android.news.contract.EverythingContract
import com.android.news.contract.FavouriteNewsContract
import com.android.news.contract.NewsDetailsContract
import com.android.news.contract.TopHeadlinesContract
import com.android.news.interactor.NewsInteractor
import com.android.news.model.Article
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class FavouriteNewsPresenter (private val newsInteractor: NewsInteractor): BasePresenter<FavouriteNewsContract.View>(),
    FavouriteNewsContract.Presenter{

    override fun loadData() {
        val disposable = Observable.fromCallable {
            newsInteractor.getItems()
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    view?.show(it)
                },
                {
                    Log.d("Error", it.message)
                }
            )
    }

    override fun deleteItem(article: Article) {
        Observable.fromCallable {
            newsInteractor.deleteItem(article)
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()
    }


}