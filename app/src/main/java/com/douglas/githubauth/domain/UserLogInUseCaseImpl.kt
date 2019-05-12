package com.douglas.githubauth.domain

import com.douglas.githubauth.data.local.UserDao
import com.douglas.githubauth.data.remote.UserService
import io.reactivex.Completable

class UserLogInUseCaseImpl(private val userDao: UserDao,
                           private val userService: UserService): UserLogInUseCase {

    override fun logInUser(userName: String?, password: String?): Completable {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}