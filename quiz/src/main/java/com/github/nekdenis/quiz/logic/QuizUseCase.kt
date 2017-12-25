package com.github.nekdenis.quiz.logic

import com.github.nekdenis.model.AnswersModel
import com.github.nekdenis.model.QuizModel
import com.github.nekdenis.model.internal.RepoException
import com.github.nekdenis.model.internal.RepoResult
import com.github.nekdenis.quiz.QuestionWrapper
import com.github.nekdenis.repo.AnswersRepo
import com.github.nekdenis.repo.QuizRepo
import com.github.nekdenis.util.TimeProvider
import io.reactivex.Completable
import io.reactivex.Observable
import java.util.concurrent.TimeUnit


interface QuizUseCase {
    fun goToNextQuestion(): Completable
    fun nextQuestionAvailable(): Observable<Boolean>
    fun startAgain(): Completable
    fun observeQuestion(listener: QuizListener): Observable<QuestionWrapper>
    fun selectAnswer(questionId: Int, answerId: Int): Completable
    fun observeTimer(): Observable<Long>
}

interface QuizListener {
    fun selectAnswer(questionId: Int, answerId: Int)
}

class QuizUseCaseImpl(
        val quizRepo: QuizRepo,
        val answersRepo: AnswersRepo,
        val timeProvider: TimeProvider
) : QuizUseCase {

    override fun goToNextQuestion(): Completable = observeQuizAndAnswers().firstOrError().flatMapCompletable { (quiz, _) ->
        answersRepo.observeCurrentQuestionId().firstOrError()
                .flatMapCompletable { currentId ->
                    if (currentId < quiz.questions.size - 1) answersRepo.incQuestionId()
                    else Completable.complete()
                }
    }

    override fun startAgain(): Completable = answersRepo.resetQuestionId()

    override fun nextQuestionAvailable(): Observable<Boolean> = observeQuizAndAnswers().switchMap { (quiz, _) ->
        answersRepo.observeCurrentQuestionId()
                .map { currentId ->
                    currentId < quiz.questions.size - 1
                }
    }

    override fun selectAnswer(questionId: Int, answerId: Int): Completable = answersRepo.addAnswer(questionId, answerId)

    override fun observeTimer() = answersRepo.observeStartTime()
            .map { it / 1000 + 30 }
            .switchMap { endTime ->
                Observable.interval(0, 1, TimeUnit.SECONDS)
                        .map {
                            val diff = endTime - timeProvider.now() / 1000
                            if (diff > 0) diff else 0
                        }
            }

    override fun observeQuestion(listener: QuizListener): Observable<QuestionWrapper> = observeQuizAndAnswers()
            .switchMap { (quiz, answers) ->
                answersRepo.observeCurrentQuestionId()
                        .map { wrapQuiz(answers, quiz, it, listener) }
            }

    private fun observeQuizAndAnswers(): Observable<Pair<QuizModel, AnswersModel>> = quizRepo.loadQuiz()
            .map { checkResult(it) }
            .flatMapObservable { quiz ->
                answersRepo.observeAnswers()
                        .map { quiz to it }
            }

    private fun checkResult(response: RepoResult<QuizModel>) = response.run {
        if (error != null) throw error as RepoException
        else result!!
    }
}