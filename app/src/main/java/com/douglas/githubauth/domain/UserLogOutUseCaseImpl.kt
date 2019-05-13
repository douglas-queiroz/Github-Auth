package com.douglas.githubauth.domain

import com.douglas.githubauth.data.local.UserDao
import com.douglas.githubauth.domain.exception.HasNoUserLoggedIn

class UserLogOutUseCaseImpl(private val userDao: UserDao): UserLogOutUseCase {

    override fun logOutUser() {

        if (userDao.getUserCredential() != null) {

            userDao.removeUser()
        } else {

            throw HasNoUserLoggedIn()
        }
    }
}