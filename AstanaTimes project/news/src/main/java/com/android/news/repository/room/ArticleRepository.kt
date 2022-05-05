package com.android.news.repository.room

import com.android.news.model.Article


class ArticleRepository(private val articleDAO: ArticleDAO) {
    fun saveItem(article: Article) {
        articleDAO.saveItem(article)
    }

    fun getItems(): List<Article> = articleDAO.getItems()

    fun deleteItem(article: Article) {
        articleDAO.deleteItem(article)
    }
}