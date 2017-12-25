package com.github.nekdenis.coreui.binding

import android.databinding.BindingAdapter
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.github.nekdenis.coreui.R

@BindingAdapter("asGrid")
fun asGrid(view: RecyclerView, asGrid: Boolean) {
    view.clipToPadding = false
    view.apply {
        itemAnimator = DefaultItemAnimator()
        val columnsCount = context.resources.getInteger(R.integer.grid_columns)
        if (asGrid) GridLayoutManager(context, columnsCount).apply { layoutManager = this }
        else LinearLayoutManager(context).apply { layoutManager = this }
    }
}
