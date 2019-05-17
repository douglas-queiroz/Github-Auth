package com.douglas.githubauth.di.module

import android.content.Context
import com.douglas.githubauth.Application
import com.douglas.githubauth.util.AuthorizationUtil
import com.douglas.githubauth.util.AuthorizationUtilImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class UtilModule(private val application: Application) {

    @Singleton
    @Provides
    fun providesContext() : Context = application

    @Singleton
    @Provides
    fun providesAuthorizationUtil(): AuthorizationUtil = AuthorizationUtilImpl()
    
}