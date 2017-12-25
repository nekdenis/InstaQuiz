package com.github.nekdenis.quiz

import android.databinding.DataBindingUtil
import android.databinding.ObservableBoolean
import android.databinding.ObservableField
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.github.nekdenis.App
import com.github.nekdenis.coreui.ViewModel
import com.github.nekdenis.quiz.databinding.ActivityQuizBinding
import com.github.nekdenis.quiz.logic.QuizListener
import com.github.nekdenis.quiz.logic.QuizUseCase
import com.github.nekdenis.quiz.logic.QuizUseCaseImpl

class QuizActivity : AppCompatActivity() {

    lateinit var viewModel: QuizViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: ActivityQuizBinding = DataBindingUtil.setContentView(this, R.layout.activity_quiz)
        (this.applicationContext as App).injector.run {
            //TODO: inject UseCase
            viewModel = QuizViewModel(QuizUseCaseImpl(quizRepo, answersRepo, timeProvider))
            binding.vm = viewModel
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.destroy()
    }
}

class QuizViewModel(
        val quizUseCase: QuizUseCase
) : ViewModel() {
    val question: ObservableField<QuestionWrapper> = ObservableField()
    val nextAvailable: ObservableBoolean = ObservableBoolean(false)
    val timer: ObservableField<String> = ObservableField("")
    val overtime: ObservableBoolean = ObservableBoolean(true)

    val listener = object : QuizListener {
        override fun selectAnswer(questionId: Int, answerId: Int) {
            if (!overtime.get()) {
                quizUseCase.selectAnswer(questionId, answerId)
                        .bindSubscribe()
            }
        }
    }

    init {
        quizUseCase.observeQuestion(listener)
                .doOnNext { question.set(it) }
                .bindSubscribe()

        quizUseCase.nextQuestionAvailable()
                .doOnNext { nextAvailable.set(it) }
                .bindSubscribe()

        quizUseCase.observeTimer()
                .doOnNext { overtime.set(it <= 0) }
                .doOnNext { timer.set(it.toString()) }
                .bindSubscribe()
    }

    fun goToNextQuestion() {
        quizUseCase.goToNextQuestion()
                .bindSubscribe()
    }

    fun startAgain() {
        quizUseCase.startAgain()
                .bindSubscribe()
    }
}
