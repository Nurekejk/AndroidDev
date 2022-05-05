package com.android.news.repository.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.android.news.model.Article

@Dao
interface ArticleDAO {
    @Insert
    fun saveItem(article: Article)

    @Query("SELECT * FROM  Article")
    fun getItems(): List<Article>

    @Delete
    fun deleteItem(article: Article)
}