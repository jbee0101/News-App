package com.example.newsapp.network

data class TopHeadlinesResponse(
    var status: String? = null,
    val totalResults: Int? = null,
    val articles: ArrayList<Article>? = null
)

data class Article(
    var source: Source? = null,
    var author: String? = null,
    var title: String? = null,
    var description: String? = null,
    var url: String? = null,
    var urlToImage: String? = null,
    var publishedAt: String? = null,
    var content: String? = null
)

data class Source(
    var id: String? = null,
    var name: String? = null
)