package com.douglas.githubauth.di

import com.douglas.githubauth.di.module.*
import com.douglas.githubauth.module.core.CoreActivity
import com.douglas.githubauth.module.login.LoginFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component (
    modules = [
        UtilModule::class,
        LocalModule::class,
        RemoteModule::class,
        UseCaseModule::class,
        ViewModelModule::class
    ]
)
interface AppComponent {

    fun inject(coreActivity: CoreActivity)
    fun inject(loginFragment: LoginFragment)
}