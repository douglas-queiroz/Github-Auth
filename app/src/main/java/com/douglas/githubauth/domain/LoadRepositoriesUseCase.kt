package com.douglas.githubauth.domain

import com.douglas.githubauth.domain.model.Repository
import io.reactivex.Observable

interface LoadRepositoriesUseCase {

    fun loadRepositories(): Observable<List<Repository>>
}