package com.github.nekdenis.quiz

import android.databinding.BindingAdapter
import android.support.v7.widget.RecyclerView
import com.github.nekdenis.coreui.swap
import com.github.nekdenis.quiz.databinding.ItemAnswerBinding
import com.github.nitrico.lastadapter.LastAdapter
import com.github.nitrico.lastadapter.Type

internal fun makeAdapter(list: List<Any>, stableIds: Boolean = true)
        = LastAdapter.with(list, BR.item, stableIds)

@BindingAdapter("answers")
fun answers(view: RecyclerView, list: List<AnswerWrapper>?) {
    list?.let {
        makeAdapter(it)
                .type {
                    Type<ItemAnswerBinding>(R.layout.item_answer)
                }
                .swap(view)
    }
}