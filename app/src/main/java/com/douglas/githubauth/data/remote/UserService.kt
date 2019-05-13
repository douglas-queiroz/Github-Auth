package com.douglas.githubauth.data.remote

import com.douglas.githubauth.domain.model.User
import rx.Completable
import rx.Observable

interface UserService {

    fun checkCredentials(authorization: String) : Completable

    fun fetchUser(authorization: String) : Observable<User>

}