package com.douglas.githubauth.domain

import com.douglas.githubauth.data.local.UserDao
import com.douglas.githubauth.data.remote.UserService
import com.douglas.githubauth.domain.exception.EmptyFieldException
import com.douglas.githubauth.domain.exception.InvalidCredentialException
import com.douglas.githubauth.domain.exception.WasNotAbleToSaveCredentialException
import com.douglas.githubauth.domain.model.UserCredential
import com.douglas.githubauth.util.AuthorizationUtil
import io.reactivex.Completable
import io.reactivex.CompletableObserver
import retrofit2.HttpException

class UserLogInUseCaseImpl(private val userDao: UserDao,
                           private val userService: UserService,
                           private val authorizationUtil: AuthorizationUtil): UserLogInUseCase {

    override fun logInUser(userName: String?, password: String?): Completable {

        return if (!userName.isNullOrEmpty() && !password.isNullOrEmpty()) {

            userService
                .checkCredentials(generateAuthorization(userName, password))
                .onErrorResumeNext(this::catchError)
                .mergeWith { completableObserver ->

                    saveCredential(userName, password, completableObserver)
                }

        } else {

            Completable.error(EmptyFieldException())
        }
    }

    private fun generateAuthorization(userName: String, password: String): String {

        return authorizationUtil.generateAuthorization(userName, password)
    }

    private fun saveCredential(userName: String, password: String, completableObserver: CompletableObserver) {

        val userCredential = UserCredential(userName, password)

        if (userDao.saveUserCredential(userCredential)) {

            completableObserver.onComplete()
        }else {

            completableObserver.onError(WasNotAbleToSaveCredentialException())
        }
    }

    private fun catchError(error: Throwable): Completable {

        return if (error is HttpException && error.code() == 401) {

            Completable.error(InvalidCredentialException())
        }else {

            Completable.error(error)
        }
    }
}