package com.github.nekdenis.network

import com.github.nekdenis.model.QuizResponse
import io.reactivex.Single


interface Api {
    fun getQuizes(): Single<QuizResponse>
}

class ApiFromAssets(
        private val responseParser: ResponseParser
) : Api {
    override fun getQuizes(): Single<QuizResponse> = Single.fromCallable {
        return@fromCallable responseParser.readAssets<QuizResponse>("zquestions.json")
    }
}