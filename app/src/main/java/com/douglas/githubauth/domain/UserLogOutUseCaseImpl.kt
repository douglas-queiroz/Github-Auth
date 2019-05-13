package com.douglas.githubauth.domain

import com.douglas.githubauth.data.local.UserDao
import io.reactivex.Completable

class UserLogOutUseCaseImpl(private val userDao: UserDao): UserLogOutUseCase {

    override fun logOutUser(): Completable {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}