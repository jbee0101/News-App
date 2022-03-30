package com.example.newsapp.network

import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.*

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
{
//    val publishedTime: String
//        get() {
//
//        }

    fun publishedTime(source: String): String
    {
        return if (publishedAt != null) {

            val pattern = if (source == "bbc-news")
                "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
            else
                "yyyy-MM-dd'T'HH:mm:ss'Z'"
            
            val simpleDateFormat = SimpleDateFormat(pattern, Locale.ENGLISH)
            simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"))
            val timestamp = Timestamp(simpleDateFormat.parse(publishedAt!!)!!.time)
            timestamp.toString()
        } else ""
    }
}

data class Source(
    var id: String? = null,
    var name: String? = null
)