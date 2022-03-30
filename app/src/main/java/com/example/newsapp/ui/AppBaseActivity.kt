package com.example.newsapp.ui

import android.os.Bundle
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.example.newsapp.network.ApiClient
import com.example.newsapp.network.ApiService
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

open class AppBaseActivity : AppCompatActivity()
{
    lateinit var apiService: ApiService

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)

        apiService = ApiClient.apiService()
    }

    fun showToast(message: String)
    {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}

class ActivityBindingProperty<out T : ViewDataBinding>(@LayoutRes private val resId: Int) :
    ReadOnlyProperty<AppBaseActivity, T>
{

    private var binding: T? = null

    override operator fun getValue(thisRef: AppBaseActivity, property: KProperty<*>): T = binding ?: createBinding(thisRef).also { binding = it }

    private fun createBinding(activity: AppBaseActivity): T = DataBindingUtil.setContentView(activity, resId)
}