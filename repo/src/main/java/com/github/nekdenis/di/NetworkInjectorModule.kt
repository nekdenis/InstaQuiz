package com.github.nekdenis.di

import android.content.Context
import com.github.nekdenis.network.Api
import com.github.nekdenis.network.ApiFromAssets
import com.github.nekdenis.network.ResponseParser
import com.google.gson.Gson

class NetworkInjectorModule(
        val appContext: Context
) {
    val gson: Gson by lazy { Gson() }
    val parser: ResponseParser by lazy { ResponseParser(gson, appContext) }
    val api: Api by lazy { ApiFromAssets(parser) }
}