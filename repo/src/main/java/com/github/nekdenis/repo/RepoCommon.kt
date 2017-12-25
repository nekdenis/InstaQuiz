package com.github.nekdenis.repo

import com.github.nekdenis.model.internal.RepoException
import com.github.nekdenis.model.internal.RepoResult
import io.reactivex.Single


fun <T, V> parse(
        fetcher: () -> Single<V>,
        parser: (V) -> T
) = fetcher()
        .map { RepoResult(result = parser(it)) }
        .onErrorReturn { RepoResult(result = null, error = RepoException(it)) }
