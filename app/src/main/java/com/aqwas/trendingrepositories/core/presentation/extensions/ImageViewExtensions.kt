package com.aqwas.trendingrepositories.core.presentation.extensions

import android.content.Context
import android.widget.ImageView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.aqwas.trendingrepositories.R
import com.bumptech.glide.Glide

fun ImageView.loadImage(context: Context, url: String?) {
        val circularProgressDrawable = CircularProgressDrawable(context)
        circularProgressDrawable.strokeWidth = 5f
        circularProgressDrawable.centerRadius = 30f
        circularProgressDrawable.start()
        Glide.with(context).load(url).placeholder(circularProgressDrawable)
            .error(R.color.black).into(this)
}

