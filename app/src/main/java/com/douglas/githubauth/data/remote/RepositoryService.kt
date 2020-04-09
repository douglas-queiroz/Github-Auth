package com.douglas.githubauth.data.remote

import com.douglas.githubauth.domain.model.Repository
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Header

interface RepositoryService {

    @GET("/user/repos")
    fun fetchRepositories(@Header("Authorization") authorization: String): Observable<List<Repository>>
}