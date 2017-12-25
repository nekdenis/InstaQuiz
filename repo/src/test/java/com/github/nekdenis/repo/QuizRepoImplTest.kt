package com.github.nekdenis.repo

import com.github.nekdenis.model.QuizResponse
import com.github.nekdenis.test.mockApi
import org.junit.Test

class QuizRepoImplTest {

    val quizes = prepareQuizResponse()

    val api = mockApi(quizes = quizes)

    val repo = QuizRepoImpl(api)

    @Test
    fun shouldLoadQuiz() {
        repo.loadQuiz()
                .test()
                .assertValue { it.error == null }
                .assertValue { it.result!!.questions.size == 2 }

    }

    private fun prepareQuizResponse(): QuizResponse = QuizResponse()
            .apply {
                put("testName", arrayListOf("url1", "url2"))
                put("testName2", arrayListOf("url3"))
            }
}