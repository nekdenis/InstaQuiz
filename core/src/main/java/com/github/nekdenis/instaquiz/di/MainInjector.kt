package com.github.nekdenis.instaquiz.di

import com.github.nekdenis.repo.AnswersRepo
import com.github.nekdenis.repo.QuizRepo
import com.github.nekdenis.util.TimeProvider

interface MainInjector {
    val quizRepo: QuizRepo
    val answersRepo: AnswersRepo
    val timeProvider: TimeProvider
}