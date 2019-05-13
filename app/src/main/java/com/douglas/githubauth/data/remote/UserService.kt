package com.douglas.githubauth.data.remote

import io.reactivex.Completable

interface UserService {

    fun checkCredentials(authorization: String) : Completable

}