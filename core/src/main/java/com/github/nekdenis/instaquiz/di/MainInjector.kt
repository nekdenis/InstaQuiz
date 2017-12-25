package com.github.nekdenis.instaquiz.di

import com.github.nekdenis.repo.AnswersRepo
import com.github.nekdenis.repo.QuizRepo

interface MainInjector {
    val quizRepo: QuizRepo
    val answersRepo: AnswersRepo
}