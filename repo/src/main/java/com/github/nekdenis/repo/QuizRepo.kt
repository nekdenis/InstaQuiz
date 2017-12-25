package com.github.nekdenis.repo

import com.github.nekdenis.model.QuizModel
import com.github.nekdenis.model.internal.RepoResult
import com.github.nekdenis.network.Api
import com.github.nekdenis.network.parseQuizModel
import io.reactivex.Single


class QuizRepoImpl(
        private val api: Api
) : QuizRepo {
    override fun loadQuiz(): Single<RepoResult<QuizModel>> =
            parse(fetcher = api::getQuizes,
                    parser = ::parseQuizModel
            )
}