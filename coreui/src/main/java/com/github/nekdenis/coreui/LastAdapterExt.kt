package com.github.nekdenis.coreui

import android.support.v7.widget.RecyclerView
import com.github.nitrico.lastadapter.LastAdapter


fun LastAdapter.swap(view: RecyclerView, removeAndRecyclerExistingViews: Boolean = false) {
    view.swapAdapter(this, removeAndRecyclerExistingViews)
}
