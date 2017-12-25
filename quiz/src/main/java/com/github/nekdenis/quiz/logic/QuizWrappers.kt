package com.github.nekdenis.quiz.logic

import com.github.nekdenis.model.AnswersModel
import com.github.nekdenis.model.QuizModel
import com.github.nekdenis.model.QuizQuestionModel
import com.github.nekdenis.quiz.AnswerWrapper
import com.github.nekdenis.quiz.QuestionWrapper


fun wrapQuiz(answers: AnswersModel, quiz: QuizModel, currentQuestionId: Int, listener: QuizListener): QuestionWrapper =
        quiz.questions[currentQuestionId].let { questionModel ->
            QuestionWrapper(
                    currentQuestionId,
                    wrapAnswer(currentQuestionId, questionModel, answers, listener),
                    questionModel.description
            )
        }

private fun wrapAnswer(questionId: Int, question: QuizQuestionModel, answers: AnswersModel, listener: QuizListener) =
        question.answers.mapIndexed { index, answerModel ->
            AnswerWrapper(
                    url = answerModel.url,
                    selected = isSelected(answers, questionId, index),
                    isRight = answerModel.isRight,
                    position = index,
                    questionId = questionId,
                    onSelected = listener::selectAnswer
            )
        }

private fun isSelected(answers: AnswersModel, questionId: Int, answerId: Int): Boolean =
        answers.answers[questionId] == answerId