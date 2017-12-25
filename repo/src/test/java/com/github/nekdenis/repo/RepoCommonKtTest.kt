package com.github.nekdenis.repo

import io.reactivex.Single
import org.junit.Test

class RepoCommonKtTest {
    @Test
    fun shouldReturnResultAfterParse() {
        parse(fetcher = { Single.just("2") }, parser = { value -> value.toInt() })
                .test()
                .assertValue { it.error == null && it.result == 2 }
    }

    @Test
    fun shouldReturnErrorAfterParse() {
        parse(fetcher = { Single.just("") }, parser = { value -> value.toInt() })
                .test()
                .assertValue { it.result == null && it.error!!.cause is NumberFormatException }
    }

}