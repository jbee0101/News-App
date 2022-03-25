package com.example.newsapp.ui

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.newsapp.R
import com.example.newsapp.databinding.NewsHeadlindActivityBinding

class NewsHeadlineActivity : AppBaseActivity() {

    private val binding: NewsHeadlindActivityBinding by ActivityBindingProperty(R.layout.news_headlind_activity)

    private lateinit var viewModel: NewsHeadlineViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this).get(NewsHeadlineViewModel::class.java)

        viewModel.onGetHeadlines()

        viewModel.newsResponse.observe(this) { response ->

            binding.lblNews.text = response
        }
    }
}