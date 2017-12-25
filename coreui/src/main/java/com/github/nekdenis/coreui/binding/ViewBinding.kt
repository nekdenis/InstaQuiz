package com.github.nekdenis.coreui.binding

import android.databinding.BindingAdapter
import android.view.View


@BindingAdapter("visible")
fun visible(v: View, visible: Boolean) {
    v.visibility = if (visible) View.VISIBLE else View.GONE
}