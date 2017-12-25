package com.github.nekdenis.test

import com.github.nekdenis.model.QuizResponse
import com.github.nekdenis.network.Api
import io.reactivex.Single


fun mockApi(
        quizes: QuizResponse? = null
): Api = object : Api {
    override fun getQuizes(): Single<QuizResponse> = Single.just(quizes)
}