package com.example.mike_utils

import android.widget.ImageView
import com.bumptech.glide.Glide


/**
 * use glide library to set up images. simply provide image view and string
 */
object MikeGlide {
    fun setImage(view: ImageView, imageUrl: String?) {
        if (!imageUrl.isNullOrEmpty()) {
            Glide.with(view.context)
                .load(imageUrl)
                .into(view)
        }
    }
}