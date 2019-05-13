package com.douglas.githubauth.data.remote

import com.douglas.githubauth.domain.model.User
import io.reactivex.Completable
import io.reactivex.Observable

interface UserService {

    fun checkCredentials(authorization: String) : Completable

    fun fetchUser(authorization: String) : Observable<User>

}