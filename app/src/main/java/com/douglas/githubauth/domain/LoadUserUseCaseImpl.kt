package com.douglas.githubauth.domain

import com.douglas.githubauth.data.local.UserDao
import com.douglas.githubauth.data.remote.UserService
import com.douglas.githubauth.domain.model.User
import com.douglas.githubauth.util.AuthorizationUtil
import rx.Observable

class LoadUserUseCaseImpl(private val userDao: UserDao,
                          private val userService: UserService,
                          private val authorizationUtil: AuthorizationUtil): LoadUserUseCase {

    override fun loadUser(): Observable<User> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}