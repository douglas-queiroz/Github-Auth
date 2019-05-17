package com.douglas.githubauth

import android.app.Application
import com.douglas.githubauth.di.AppComponent
import com.douglas.githubauth.di.DaggerAppComponent
import com.douglas.githubauth.di.module.UtilModule

class Application: Application() {

    companion object {
        lateinit var component: AppComponent
    }

    override fun onCreate() {
        super.onCreate()

        initDagger()
    }

    private fun initDagger() {
        component = DaggerAppComponent
            .builder()
            .utilModule(UtilModule(this))
            .build()
    }
}