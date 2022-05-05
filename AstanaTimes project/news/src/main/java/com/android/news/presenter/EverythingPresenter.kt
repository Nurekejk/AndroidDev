package com.android.news.presenter

import android.util.Log
import com.android.common.BasePresenter
import com.android.news.contract.EverythingContract
import com.android.news.contract.TopHeadlinesContract
import com.android.news.interactor.NewsInteractor
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class EverythingPresenter (private val newsInteractor: NewsInteractor): BasePresenter<EverythingContract.View>(),
    EverythingContract.Presenter{

    private var page = 1

    override fun loadData(keyword: String, pageSize: Int) {
        val disposable = newsInteractor.getEverything(keyword, pageSize, page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    view?.show(it.articles)
                },
                {
                    Log.d("Error", it.message)
                }
            )
    }

    override fun incrementPage() {
        page += 1
        Log.d("PAGE", page.toString())

    }

    override fun loadMoreData(keyword: String, pageSize: Int) {
        val disposable = newsInteractor.getEverything(keyword, pageSize, page)
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