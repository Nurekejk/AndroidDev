package com.android.common

interface MvpPresenter<V : MvpView> {

    fun attach(view: V)

    fun detach()
}