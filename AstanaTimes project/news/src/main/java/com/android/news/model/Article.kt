package com.android.news.model

import androidx.annotation.Nullable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class Article(
    @Nullable val author: String?,
    @Nullable val content: String?,
    val description: String,
    val publishedAt: String,
    @Embedded val source: Source,
    @PrimaryKey val title: String,
    val url: String,
    val urlToImage: String
) : Serializable