package com.douglas.githubauth.di.module

import com.douglas.githubauth.data.local.UserDao
import com.douglas.githubauth.data.remote.UserService
import com.douglas.githubauth.domain.*
import com.douglas.githubauth.helper.UserSessionHelper
import com.douglas.githubauth.util.AuthorizationUtil
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class UseCaseModule {

    @Singleton
    @Provides
    fun providesCheckLoginUseCase(userDao: UserDao) : CheckLoginUseCase = CheckLoginUseCaseImpl(userDao)

    @Singleton
    @Provides
    fun providesLoadUserUseCase(userDao: UserDao, userService: UserService, authorizationUtil: AuthorizationUtil) :
            LoadUserUseCase = LoadUserUseCaseImpl(userDao, userService, authorizationUtil)

    @Singleton
    @Provides
    fun providesUserLogInUseCase(userDao: UserDao, userService: UserService, authorizationUtil: AuthorizationUtil) :
            UserLogInUseCase = UserLogInUseCaseImpl(userDao, userService, authorizationUtil)

    @Singleton
    @Provides
    fun providesUserLogOut(userDao: UserDao) : UserLogOutUseCase = UserLogOutUseCaseImpl(userDao)
}