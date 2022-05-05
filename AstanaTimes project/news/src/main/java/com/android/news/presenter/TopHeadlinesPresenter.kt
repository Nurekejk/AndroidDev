package com.android.news.presenter

import android.util.Log
import com.android.common.BasePresenter
import com.android.news.contract.TopHeadlinesContract
import com.android.news.interactor.NewsInteractor
import com.android.news.ui.KEYWORD
import com.android.news.ui.PAGE_SIZE
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit


class TopHeadlinesPresenter (private val newsInteractor: NewsInteractor): BasePresenter<TopHeadlinesContract.View>(),
    TopHeadlinesContract.Presenter{

    private var firstProgressBarFlag = true
    private var page = 1

    override fun loadData(keyword: String, pageSize: Int) {
        val disposable = newsInteractor.getTopHeadlines(keyword, pageSize, page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                if(firstProgressBarFlag)
                    view?.showProgressBar()
            }
            .subscribe(
                {
                    view?.hideProgressBar()
                    view?.show(it.articles)
                },
                {
                    Log.d("Error", it.message)
                }
            )
        firstProgressBarFlag = false
    }

    override fun reloadData() {
        page = 1
        val disposable = Observable.interval(1000, 5000,
            TimeUnit.MILLISECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    loadData(KEYWORD, PAGE_SIZE)
                    Log.d("reload", "true")
                },
                {
                    Log.d("Error", it.message)
                }
            )
    }

    override fun incrementPage() {
        page += 1
    }

    override fun loadMoreData(keyword: String, pageSize: Int) {
        val disposable = newsInteractor.getTopHeadlines(keyword, pageSize, page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    view?.showMore(it.articles)
                },
                {
                    Log.d("Error", it.message)
                }
            )
    }


}