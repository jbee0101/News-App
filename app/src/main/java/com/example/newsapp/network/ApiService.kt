package com.example.newsapp.network

import com.example.newsapp.utils.Constants
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService
{
    @GET("top-headlines")
    fun GetTopHeadlines(@Query("sources") sources: String = Constants.SOURCE,
                        @Query("apiKey") apiKey: String = Constants.API_KEY): Call<TopHeadlinesResponse>
}