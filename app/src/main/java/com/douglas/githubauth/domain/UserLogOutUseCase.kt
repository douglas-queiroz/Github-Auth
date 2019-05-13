package com.douglas.githubauth.domain

import io.reactivex.Completable

interface UserLogOutUseCase {

    fun logOutUser(): Completable
}