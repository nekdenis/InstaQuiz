package com.github.nekdenis.instaquiz.di

import android.content.Context
import com.github.nekdenis.di.NetworkInjectorModule
import com.github.nekdenis.repo.AnswersRepoImpl
import com.github.nekdenis.repo.QuizRepoImpl
import com.github.nekdenis.repo.prefs.AppPrefsBinImpl
import com.github.nekdenis.repo.prefs.initBinaryPreferences
import com.github.nekdenis.util.TimeProviderImpl

class AppInjector(
        val appContext: Context
) : MainInjector {
    private val networkInjectorModule by lazy { NetworkInjectorModule(appContext) }
    private val appPrefsRepo by lazy { AppPrefsBinImpl(initBinaryPreferences(appContext)) }

    override val timeProvider by lazy { TimeProviderImpl() }
    override val quizRepo by lazy { QuizRepoImpl(networkInjectorModule.api) }
    override val answersRepo by lazy { AnswersRepoImpl(appPrefsRepo, timeProvider) }
}
