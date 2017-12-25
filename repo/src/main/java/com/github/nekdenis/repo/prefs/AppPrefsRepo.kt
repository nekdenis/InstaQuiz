package com.github.nekdenis.repo.prefs

import android.annotation.SuppressLint
import android.content.Context
import com.github.nekdenis.model.AnswersModel
import com.ironz.binaryprefs.BinaryPreferencesBuilder
import com.ironz.binaryprefs.Preferences
import com.ironz.binaryprefs.serialization.serializer.persistable.Persistable
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject


interface AppPrefsRepo {
    fun <T : Any> observe(key: String, default: T): Observable<T>
    fun <T : Any> put(key: String, value: T): Completable
    fun clear(): Completable
}

@Suppress("UNCHECKED_CAST")
class AppPrefsBinImpl(
        private val binaryPreferences: Preferences
) : AppPrefsRepo {

    private val keyChanges = PublishSubject.create<String>()

    override fun <T : Any> put(key: String, value: T): Completable = Completable.fromAction { putValue(value, key) }

    override fun <T : Any> observe(key: String, default: T): Observable<T> = asObservable(key, default)

    @SuppressLint("ApplySharedPref")
    override fun clear(): Completable = Completable.fromAction {
        binaryPreferences.edit().clear().commit()
    }

    private fun <T : Any> putValue(value: T, key: String) {
        binaryPreferences.edit().run {
            when (value) {
                is Boolean     -> putBoolean(key, value)
                is Int         -> putInt(key, value)
                is Long        -> putLong(key, value)
                is Float       -> putFloat(key, value)
                is String      -> putString(key, value)
                is Persistable -> putPersistable(key, value)
                else           -> throw IllegalArgumentException("can't observe preference with non primitive type")
            }.apply()
        }
        keyChanges.onNext(key)
    }

    private fun <T : Any> asObservable(key: String, default: T): Observable<T> = keyChanges
            .filter(key::equals)
            .startWith("initial emit")
            .map { getValue(key, default) as T }

    private fun <T : Any> getValue(key: String, default: T): Any = binaryPreferences.run {
        when (default) {
            is Boolean     -> getBoolean(key, default)
            is Int         -> getInt(key, default)
            is Long        -> getLong(key, default)
            is Float       -> getFloat(key, default)
            is String      -> getString(key, default)
            is Persistable -> getPersistable(key, default)
            else           -> throw IllegalArgumentException("PREFERENCES:: can't observe preference with non primitive type")
        }
    }
}

fun initBinaryPreferences(context: Context): Preferences =
        BinaryPreferencesBuilder(context)
                .registerPersistable(AnswersModel.KEY, AnswersModel::class.java)
                .build()