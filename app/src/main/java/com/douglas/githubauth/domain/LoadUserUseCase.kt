package com.douglas.githubauth.domain

import com.douglas.githubauth.domain.model.User
import io.reactivex.Observable

interface LoadUserUseCase {

    fun loadUser(): Observable<User>
}