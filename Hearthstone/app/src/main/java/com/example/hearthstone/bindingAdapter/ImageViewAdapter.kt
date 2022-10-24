package com.example.hearthstone.bindingAdapter

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.hearthstone.R

@BindingAdapter("android:glide")
fun setImage(imageView: ImageView, url: String?) {
    //Check if url is null
    url?.let {
        Glide.with(imageView.context)
            .load(url)
            .placeholder(R.drawable.photo_copy_2)
            .into(imageView)
    } ?: imageView.setImageResource(R.drawable.photo_copy_2)
}
