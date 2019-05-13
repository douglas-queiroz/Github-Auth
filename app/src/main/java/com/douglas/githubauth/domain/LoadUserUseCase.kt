package com.douglas.githubauth.domain

import com.douglas.githubauth.domain.model.User
import rx.Observable

interface LoadUserUseCase {

    fun loadUser(): Observable<User>
}