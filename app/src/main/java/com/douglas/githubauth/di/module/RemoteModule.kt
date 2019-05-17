package com.douglas.githubauth.di.module

import com.douglas.githubauth.data.remote.RetrofitCreator
import com.douglas.githubauth.data.remote.UserService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton


@Module
class RemoteModule {

    @Singleton
    @Provides
    fun providesRetrofit() : Retrofit = RetrofitCreator.createRetrofit()

    @Singleton
    @Provides
    fun providesUserService(retrofit: Retrofit) : UserService = retrofit.create(UserService::class.java)
}