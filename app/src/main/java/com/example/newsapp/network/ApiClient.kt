package com.example.newsapp.network

import com.example.newsapp.utils.Constants
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


class ApiClient
{
    internal val apiService: ApiService

    init
    {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        val okHttpClient = OkHttpClient.Builder()
            .readTimeout(25, TimeUnit.SECONDS)
            .connectTimeout(25, TimeUnit.SECONDS)
            .addInterceptor(loggingInterceptor).build()

        val builder = GsonBuilder()

        builder.setLenient()
        val gson = builder.create()

        val retrofit = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

        apiService = retrofit.create(ApiService::class.java)
    }

    companion object
    {
        private var mApiClient: ApiClient? = null

        fun apiService(): ApiService
        {
            if (mApiClient == null)
                mApiClient = ApiClient()

            return mApiClient!!.apiService
        }
    }
}