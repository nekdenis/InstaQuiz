package com.github.nekdenis.network

import com.github.nekdenis.model.QuizResponse
import org.junit.Assert
import org.junit.Test

class QuizModelParserKtTest {

    @Test
    fun shouldParseQuizModel() {
        QuizResponse()
                //prepare
                .apply {
                    put("testName", arrayListOf("url1", "url2"))
                    put("testName2", arrayListOf("url3"))
                }
                //parse
                .let { parseQuizModel(it) }
                //check
                .run {
                    Assert.assertEquals(2, questions.size)
                    Assert.assertEquals("testName2", questions[1].description)
                    Assert.assertEquals(2, questions[0].answers.size)
                    Assert.assertEquals("url2", questions[0].answers[1].url)
                }
    }
}