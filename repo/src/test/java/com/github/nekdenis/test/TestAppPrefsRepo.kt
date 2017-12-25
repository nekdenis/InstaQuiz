package com.github.nekdenis.test;

import com.github.nekdenis.repo.prefs.AppPrefsRepo
import io.reactivex.Completable
import io.reactivex.Observable

class TestAppPrefsRepo : AppPrefsRepo {
    val vals = HashMap<String, Any>()

    override fun <T : Any> observe(key: String, default: T): Observable<T> = Observable.just(vals.getOrDefault(key, default) as T)

    override fun <T : Any> put(key: String, value: T): Completable = Completable.fromAction {
        vals.put(key, value)
    }

    override fun clear(): Completable = Completable.fromAction {
        vals.clear()
    }
}