package com.github.nekdenis.coreui.binding

import android.databinding.BindingAdapter
import android.widget.ImageView
import com.bumptech.glide.Glide


object ImageBindingAdapter {
    @JvmStatic
    @BindingAdapter("imageUrl")
    fun setImageUrl(view: ImageView, url: String) {
        //TODO: inject
        Glide.with(view.context).load(url).into(view)
    }
}