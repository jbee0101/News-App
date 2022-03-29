package com.example.newsapp.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.newsapp.R

@BindingAdapter("imageUrl")
fun loadImage(view: ImageView, imageUrl: String?) {
    if (imageUrl != null && imageUrl.isNotEmpty()) {
        Glide.with(view.context)
            .load(imageUrl)
            .fitCenter()
            .error(R.drawable.ic_no_image)
            .into(view)
    } else {
        view.setImageResource(R.drawable.ic_no_image)
    }
}
