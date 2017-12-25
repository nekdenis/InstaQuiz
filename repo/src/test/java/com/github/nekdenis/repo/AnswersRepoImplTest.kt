package com.github.nekdenis.repo

import com.github.nekdenis.repo.prefs.AppPrefsBinImpl
import com.github.nekdenis.repo.prefs.initBinaryPreferences
import org.junit.Test

class AnswersRepoImplTest {
    val repo = AnswersRepoImpl(AppPrefsBinImpl(initBinaryPreferences()))

    @Test
    fun testRepo() {
        repo.addAnswer(1, 4)
                .andThen(repo.addAnswer(2, 3))
                .andThen(repo.observeAnswers())
                .test()
                .assertValue { it.answers.size == 2 }
                .assertValue { it.answers[1] == 3 }
    }
}