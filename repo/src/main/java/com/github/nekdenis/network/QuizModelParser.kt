package com.github.nekdenis.network

import com.github.nekdenis.model.AnswerModel
import com.github.nekdenis.model.QuizModel
import com.github.nekdenis.model.QuizQuestionModel
import com.github.nekdenis.model.QuizResponse
import java.util.ArrayList


fun parseQuizModel(response: QuizResponse) =
        QuizModel(
                response.map { (description, images) -> QuizQuestionModel(description, parseQuestionModel(images)) }
        )

private fun parseQuestionModel(images: ArrayList<String>): List<AnswerModel> =
        images.mapIndexed { i, it -> AnswerModel(i == 0, it) }