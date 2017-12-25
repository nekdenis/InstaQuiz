package com.github.nekdenis.model.internal


data class RepoResult<out T>(
        val result: T?,
        val error: RepoException? = null
)