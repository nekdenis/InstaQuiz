package com.github.nekdenis.util


interface TimeProvider {
    fun now(): Long
}

class TimeProviderImpl : TimeProvider {
    override fun now(): Long = System.currentTimeMillis()
}