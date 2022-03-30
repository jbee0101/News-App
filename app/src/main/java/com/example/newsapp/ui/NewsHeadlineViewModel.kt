package com.example.newsapp.ui

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.newsapp.network.ApiClient
import com.example.newsapp.network.Article
import com.example.newsapp.network.TopHeadlinesResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class NewsHeadlineViewModel: ViewModel()
{
    val newsResponse = MutableLiveData<TopHeadlinesResponse>()
    var mHeadlinesList: ArrayList<Article> = ArrayList()


    fun onGetHeadlines(source: String)
    {
        val call = ApiClient.apiService().GetTopHeadlines(sources = source)
        call.enqueue(object : Callback<TopHeadlinesResponse>{
            override fun onFailure(call: Call<TopHeadlinesResponse>, t: Throwable) {
                Log.e("NewsHeadlineViewModel", "onFailure: ${t.localizedMessage}")
            }

            override fun onResponse(call: Call<TopHeadlinesResponse>, response: Response<TopHeadlinesResponse>)
            {
                Log.e("NewsHeadlineViewModel", "onResponse: ${response.body()}")
                if (response.isSuccessful)
                {
                    val body = response.body() as TopHeadlinesResponse
                    body.articles ?: return

                    mHeadlinesList.clear()
                    mHeadlinesList.addAll(body.articles)

                    newsResponse.postValue(body)
                }
            }
        })
    }
}