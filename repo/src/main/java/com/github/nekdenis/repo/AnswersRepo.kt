package com.github.nekdenis.repo

import com.github.nekdenis.model.AnswersModel
import com.github.nekdenis.repo.prefs.AppPrefsRepo
import com.github.nekdenis.util.TimeProvider
import io.reactivex.Completable
import io.reactivex.Observable


class AnswersRepoImpl(
        val prefs: AppPrefsRepo,
        val timeProvider: TimeProvider
) : AnswersRepo {

    val KEY_CURRENT_QUESTION = "KEY_CURRENT_QUESTION"
    val KEY_TIME_STARTED_QUESTION = "KEY_TIME_STARTED_QUESTION"

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

    override fun observeCurrentQuestionId(): Observable<Int> =
            prefs.observe(KEY_CURRENT_QUESTION, 0)

    override fun incQuestionId(): Completable =
            prefs.observe(KEY_CURRENT_QUESTION, 0)
                    .firstOrError()
                    .flatMapCompletable { id ->
                        saveQuestionId(id + 1)
                    }

    override fun resetQuestionId(): Completable = saveQuestionId(0)

    override fun observeStartTime(): Observable<Long> = prefs.observe(KEY_TIME_STARTED_QUESTION, timeProvider.now())

    private fun saveQuestionId(id: Int): Completable = prefs.put(KEY_CURRENT_QUESTION, id)
            .andThen(prefs.put(KEY_TIME_STARTED_QUESTION, timeProvider.now()))
}