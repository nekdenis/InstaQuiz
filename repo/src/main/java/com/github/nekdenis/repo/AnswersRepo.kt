package com.github.nekdenis.repo

import com.github.nekdenis.model.AnswersModel
import com.github.nekdenis.repo.prefs.AppPrefsRepo
import io.reactivex.Completable
import io.reactivex.Observable


class AnswersRepoImpl(
        val prefs: AppPrefsRepo
) : AnswersRepo {

    val KEY_CURRENT_QUESTION = "KEY_CURRENT_QUESTION"

    override fun observeAnswers(): Observable<AnswersModel> =
            prefs.observe(AnswersModel.KEY, AnswersModel())

    override fun addAnswer(questionId: Int, answerId: Int): Completable =
            observeAnswers()
                    .firstOrError()
                    .flatMapCompletable {
                        prefs.put(AnswersModel.KEY, addAnswerToModel(it, questionId, answerId))
                    }

    private fun addAnswerToModel(it: AnswersModel, questionId: Int, answerId: Int): AnswersModel {
        val newAnswers = HashMap(it.answers)
        newAnswers.put(questionId, answerId)
        return AnswersModel(newAnswers)
    }

    override fun currentQuestionId(): Observable<Int> =
            prefs.observe(KEY_CURRENT_QUESTION, 0)

    override fun incQuestionId(): Completable =
            prefs.observe(KEY_CURRENT_QUESTION, 0)
                    .firstOrError()
                    .flatMapCompletable { id ->
                        prefs.put(KEY_CURRENT_QUESTION, id + 1)
                    }

    override fun resetQuestionId(): Completable = prefs.put(KEY_CURRENT_QUESTION, 0)

}