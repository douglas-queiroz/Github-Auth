package com.douglas.githubauth.di.module

import android.content.Context
import com.douglas.githubauth.data.local.UserDao
import com.douglas.githubauth.data.local.UserDaoImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class LocalModule {

    @Singleton
    @Provides
    fun providesUserDao(context: Context): UserDao = UserDaoImpl(context)
}