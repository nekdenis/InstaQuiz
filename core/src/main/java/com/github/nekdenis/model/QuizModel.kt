package com.github.nekdenis.model


class QuizResponse : LinkedHashMap<String, ArrayList<String>>()

class QuizModel(
        val questions: List<QuizQuestionModel>
)

data class QuizQuestionModel(
        val description: String,
        val answers: List<AnswerModel>
)

data class AnswerModel(
        val isRight: Boolean,
        val url: String
)

