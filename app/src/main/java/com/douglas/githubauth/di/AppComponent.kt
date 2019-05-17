package com.douglas.githubauth.di

import com.douglas.githubauth.di.module.LocalModule
import com.douglas.githubauth.di.module.RemoteModule
import com.douglas.githubauth.di.module.UseCaseModule
import com.douglas.githubauth.di.module.UtilModule
import com.douglas.githubauth.module.core.CoreActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component (
    modules = [
        UtilModule::class,
        LocalModule::class,
        RemoteModule::class,
        UseCaseModule::class
    ]
)
interface AppComponent {

    fun inject(coreActivity: CoreActivity)
}