package com.github.nekdenis.repo

import com.github.nekdenis.model.AnswersModel
import com.github.nekdenis.model.QuizModel
import com.github.nekdenis.model.internal.RepoResult
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

interface QuizRepo {
    fun loadQuiz(): Single<RepoResult<QuizModel>>
}

interface AnswersRepo {
    fun observeAnswers(): Observable<AnswersModel>
    fun addAnswer(questionId: Int, answerId: Int): Completable
    fun currentQuestionId(): Observable<Int>
    fun incQuestionId(): Completable
    fun resetQuestionId(): Completable
}