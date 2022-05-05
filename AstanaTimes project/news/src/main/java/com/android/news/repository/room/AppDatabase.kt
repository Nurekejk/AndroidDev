package com.android.news.repository.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.android.news.model.Article

@Database (entities = [(Article::class)], version = 1)
abstract class AppDatabase : RoomDatabase(){
    abstract fun articleDAO() : ArticleDAO
}