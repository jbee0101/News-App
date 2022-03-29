package com.example.newsapp.ui

import android.os.Bundle
import com.example.newsapp.R
import com.example.newsapp.databinding.HeadlineDetailActivityBinding
import com.example.newsapp.network.Article
import com.example.newsapp.utils.deserialize

class HeadlineDetailActivity : AppBaseActivity() {

    private val binding: HeadlineDetailActivityBinding by ActivityBindingProperty(R.layout.headline_detail_activity)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val article = intent.getStringExtra("article")?.deserialize(Article::class.java)
        binding.item = article
        binding.appBar.lblTitle.text = article?.author
    }
}