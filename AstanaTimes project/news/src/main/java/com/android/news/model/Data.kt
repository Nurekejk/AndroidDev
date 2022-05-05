package com.android.news.model

data class Data(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)