package com.douglas.githubauth.domain

import rx.Completable


interface UserLogInUseCase {

    fun logInUser(userName: String?, password: String?): Completable
}