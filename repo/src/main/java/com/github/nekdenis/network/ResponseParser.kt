package com.github.nekdenis.network

import android.content.Context
import com.google.gson.Gson
import java.io.InputStream
import java.io.InputStreamReader

class ResponseParser(
        val gson: Gson,
        val context: Context
) {
    inline fun <reified T : Any> readAssets(path: String): T =
            context.assets.open(path).use { parseAssets(it) }

    inline fun <reified T : Any> parseAssets(it: InputStream): T {
        InputStreamReader(it).use { reader ->
            return gson.fromJson(reader, T::class.java)
        }
    }
}