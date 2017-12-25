package com.github.nekdenis.instaquiz.di

import android.app.Application
import com.github.nekdenis.App


class RealApp : App, Application() {
    override val injector: MainInjector by lazy { AppInjector(this) }
}