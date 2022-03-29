package com.example.newsapp.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapp.R
import com.example.newsapp.common.GenericAdapter
import com.example.newsapp.databinding.NewsHeadlindActivityBinding
import com.example.newsapp.network.Article
import com.example.newsapp.utils.serialize

class NewsHeadlineActivity : AppBaseActivity() {

    val TAG = "NewsHeadlineActivity"

    private val binding: NewsHeadlindActivityBinding by ActivityBindingProperty(R.layout.news_headlind_activity)

    private lateinit var viewModel: NewsHeadlineViewModel
    private lateinit var mAdapter: GenericAdapter<Article>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this).get(NewsHeadlineViewModel::class.java)

        viewModel.onGetHeadlines()

        mAdapter = GenericAdapter(viewModel.mHeadlinesList, this, R.layout.news_headline_cell)
        { view: View, article: Article ->
            Log.e(TAG, "GenericAdapter onClickItem:  $article")

            val intent = Intent(this, HeadlineDetailActivity::class.java)
            intent.putExtra("article", article.serialize())
            startActivity(intent)
        }

        val layoutManager = LinearLayoutManager(this)
        binding.rvHeadlines.layoutManager = layoutManager
        binding.rvHeadlines.adapter = mAdapter

        viewModel.newsResponse.observe(this) { response ->

            Log.e(TAG, "newsResponse: $response")
            binding.appBar.lblTitle.text = response.articles!![0].author
            binding.pbLoading.visibility = View.GONE

            mAdapter.notifyDataSetChanged()

        }
    }
}