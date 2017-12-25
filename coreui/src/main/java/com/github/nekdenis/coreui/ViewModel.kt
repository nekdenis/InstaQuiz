package com.github.nekdenis.coreui

import android.util.Log
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable


private val onNextStub: (Any) -> Unit = {}
private val onErrorStub: (Throwable) -> Unit = { Log.e("ViewModel", "On error not implemented") }
private val onCompleteStub: () -> Unit = {}

abstract class ViewModel {
    val disposable: CompositeDisposable = CompositeDisposable()

    fun Disposable.bind() {
        disposable.add(this)
    }

    fun <T : Any> Observable<T>.bindSubscribe(
            onNext: (T) -> Unit = onNextStub,
            onError: (Throwable) -> Unit = onErrorStub,
            onComplete: () -> Unit = onCompleteStub
    ) = subscribe(onNext, onError, onComplete).bind()

    fun Completable.bindSubscribe(
            onComplete: () -> Unit = onCompleteStub,
            onError: (Throwable) -> Unit = onErrorStub
    ) = subscribe(onComplete, onError).bind()

    fun destroy() {
        disposable.clear()
    }
}