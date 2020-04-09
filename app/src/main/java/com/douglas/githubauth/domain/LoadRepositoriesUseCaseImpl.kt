package com.douglas.githubauth.domain

import com.douglas.githubauth.data.local.UserDao
import com.douglas.githubauth.data.remote.RepositoryService
import com.douglas.githubauth.domain.exception.HasNoUserLoggedIn
import com.douglas.githubauth.domain.model.Repository
import com.douglas.githubauth.domain.model.UserCredential
import com.douglas.githubauth.util.AuthorizationUtil
import io.reactivex.Observable

class LoadRepositoriesUseCaseImpl(
    private val repositoryService: RepositoryService,
    private val authorizationUtil: AuthorizationUtil,
    private val userDao: UserDao
): LoadRepositoriesUseCase {

    override fun loadRepositories(): Observable<List<Repository>> {

        val userCredential = userDao.getUserCredential()

        return if (userCredential != null) {

            val authorization = getAuthorization(userCredential)

            return repositoryService.fetchRepositories(authorization)

        } else {

            Observable.error(HasNoUserLoggedIn())
        }
    }

    private fun getAuthorization(userCredential: UserCredential) : String {

        return authorizationUtil.generateAuthorization(userCredential.userName, userCredential.password)
    }
}