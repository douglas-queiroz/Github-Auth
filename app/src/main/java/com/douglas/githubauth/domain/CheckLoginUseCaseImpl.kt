package com.douglas.githubauth.domain

import com.douglas.githubauth.helper.UserSessionHelper

class CheckLoginUseCaseImpl(private val userSessionHelper: UserSessionHelper): CheckLoginUseCase {

    override fun hasUserLogged(): Boolean {

        return !userSessionHelper.getUserToken().isNullOrEmpty()
    }
}