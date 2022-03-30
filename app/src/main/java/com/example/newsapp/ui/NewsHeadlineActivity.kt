package com.example.newsapp.ui

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapp.R
import com.example.newsapp.common.GenericAdapter
import com.example.newsapp.databinding.NewsHeadlindActivityBinding
import com.example.newsapp.network.Article
import com.example.newsapp.utils.serialize
import java.util.concurrent.Executor

class NewsHeadlineActivity : AppBaseActivity() {

    val TAG = "NewsHeadlineActivity"

    private val binding: NewsHeadlindActivityBinding by ActivityBindingProperty(R.layout.news_headlind_activity)

    private lateinit var viewModel: NewsHeadlineViewModel
    private lateinit var mAdapter: GenericAdapter<Article>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this).get(NewsHeadlineViewModel::class.java)

        detectTouchId()


//        viewModel.onGetHeadlines()

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
            binding.appBar.lblTitle.text = response.articles!![0].source?.name
            binding.pbLoading.visibility = View.GONE

            mAdapter.notifyDataSetChanged()

        }
    }

    fun detectTouchId()
    {
        if (!packageManager.hasSystemFeature(PackageManager.FEATURE_FINGERPRINT))
        {
            viewModel.onGetHeadlines(getString(R.string.source))
            return
        }

        val executor = ContextCompat.getMainExecutor(this)
        val biometricManager = BiometricManager.from(this)

        when (biometricManager.canAuthenticate()) {
            BiometricManager.BIOMETRIC_SUCCESS ->{
                authUser(executor)
            }
            BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE,
            BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE,
            BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED->{
                viewModel.onGetHeadlines(getString(R.string.source))
            }
        }
    }

    private fun authUser(executor: Executor) {
        val promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Authentication Required")
            .setSubtitle("Important Authentication")
            .setNegativeButtonText("Cancel")
            .setAllowedAuthenticators(BiometricManager.Authenticators.BIOMETRIC_WEAK)
            .build()

        val biometricPrompt = BiometricPrompt(this, executor,
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationSucceeded(
                    result: BiometricPrompt.AuthenticationResult
                ) {
                    super.onAuthenticationSucceeded(result)
                    viewModel.onGetHeadlines(getString(R.string.source))

                }

                override fun onAuthenticationError(
                    errorCode: Int, errString: CharSequence
                ) {
                    super.onAuthenticationError(errorCode, errString)
                    finish()
                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    finish()
                }
            })

        biometricPrompt.authenticate(promptInfo)
    }

}