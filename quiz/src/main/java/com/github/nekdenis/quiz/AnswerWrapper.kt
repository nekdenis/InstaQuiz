package com.github.nekdenis.quiz

import com.github.nitrico.lastadapter.StableId

class AnswerWrapper(
        val url: String,
        val selected: Boolean,
        val isRight: Boolean,
        val questionId: Int,
        val position: Int,
        val onSelected: (questionId: Int, answerId: Int) -> Unit
) : StableId {
    override val stableId: Long = position.toLong()

    fun onClick(): Unit = onSelected(questionId, position)
}

class QuestionWrapper(
        val id: Int,
        val answers: List<AnswerWrapper>,
        val description: String
)