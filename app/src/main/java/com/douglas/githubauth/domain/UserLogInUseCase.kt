package com.douglas.githubauth.domain

import io.reactivex.Completable


interface UserLogInUseCase {

    fun logInUser(userName: String?, password: String?): Completable
}