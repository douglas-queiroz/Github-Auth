package com.douglas.githubauth.data.remote

import com.douglas.githubauth.domain.model.User
import io.reactivex.Completable
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Header

interface UserService {

    @GET("/")
    fun checkCredentials(@Header("Authorization") authorization: String) : Completable

    @GET("user")
    fun fetchUser(@Header("Authorization") authorization: String) : Observable<User>

}