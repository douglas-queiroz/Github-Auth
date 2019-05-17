package com.douglas.githubauth.domain

import com.douglas.githubauth.data.local.UserDao

class CheckLoginUseCaseImpl(private val userDao: UserDao): CheckLoginUseCase {

    override fun hasUserLogged(): Boolean {

        return userDao.getUserCredential() != null
    }
}