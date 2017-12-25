package com.github.nekdenis.network

import android.test.mock.MockContext
import com.github.nekdenis.di.NetworkInjectorModule
import com.github.nekdenis.model.QuizResponse
import org.junit.Assert
import org.junit.Test

class ApiFromAssetsTest {

    val injector = NetworkInjectorModule(MockContext())
    val parser = injector.parser

    @Test
    fun shouldParseQuestions() {
        val response: QuizResponse = parser.parseAssets(readFromAssets("testquestions.json"))
        Assert.assertEquals(2, response.size)
        Assert.assertEquals(3, response["TestName"]!!.size)
        Assert.assertEquals("https://url4.jpg", response["TestName2"]!![1])
    }

    private fun readFromAssets(filePath: String) = this.javaClass.classLoader.getResourceAsStream(filePath)

}