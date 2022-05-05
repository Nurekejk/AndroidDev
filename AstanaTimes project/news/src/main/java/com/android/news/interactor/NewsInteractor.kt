package com.android.news.interactor

import com.android.news.model.Article
import com.android.news.model.Data
import com.android.news.repository.api.ApiService
import com.android.news.repository.room.ArticleRepository
import io.reactivex.Single

class NewsInteractor(private val apiService: ApiService, private val articleRepository: ArticleRepository) {
    fun getTopHeadlines(keyword: String, pageSize:Int, page:Int): Single<Data> = apiService.getTopHeadlines(keyword, pageSize, page)
    fun getEverything(keyword: String, pageSize:Int, page:Int): Single<Data> = apiService.getEverything(keyword, pageSize, page)
    fun saveItem(article: Article){ articleRepository.saveItem(article) }
    fun getItems(): List<Article> = articleRepository.getItems()
    fun deleteItem(article: Article) { articleRepository.deleteItem(article) }
}