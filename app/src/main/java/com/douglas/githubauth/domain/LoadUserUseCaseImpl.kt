package com.douglas.githubauth.domain

import com.douglas.githubauth.data.local.UserDao
import com.douglas.githubauth.data.remote.UserService
import com.douglas.githubauth.domain.exception.HasNoUserLoggedIn
import com.douglas.githubauth.domain.exception.InvalidCredentialException
import com.douglas.githubauth.domain.model.User
import com.douglas.githubauth.domain.model.UserCredential
import com.douglas.githubauth.util.AuthorizationUtil
import retrofit2.HttpException
import rx.Observable

class LoadUserUseCaseImpl(private val userDao: UserDao,
                          private val userService: UserService,
                          private val authorizationUtil: AuthorizationUtil): LoadUserUseCase {

    override fun loadUser(): Observable<User> {

        val userCredential = userDao.getUserCredential()

        return if (userCredential != null) {

            val authorization = getAuthorization(userCredential)

            userService.fetchUser(authorization)
                .onErrorResumeNext(this::catchError)

        } else {

            Observable.error(HasNoUserLoggedIn())
        }
    }

    private fun getAuthorization(userCredential: UserCredential) : String {

        return authorizationUtil.generateAuthorization(userCredential.userName, userCredential.password)
    }

    private fun catchError(error: Throwable) : Observable<User> {

        return if (error is HttpException && error.code() == 401) {

            Observable.error(InvalidCredentialException())
        } else {

            Observable.error(error)
        }
    }
}