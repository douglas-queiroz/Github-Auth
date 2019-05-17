package com.douglas.githubauth.module.login

import android.arch.lifecycle.MutableLiveData
import com.douglas.githubauth.R
import com.douglas.githubauth.domain.UserLogInUseCase
import com.douglas.githubauth.domain.exception.EmptyFieldException
import com.douglas.githubauth.domain.exception.InvalidCredentialException
import com.douglas.githubauth.domain.exception.WasNotAbleToSaveCredentialException
import com.douglas.githubauth.module.base.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class LoginViewModel @Inject constructor(private val userLogInUseCase: UserLogInUseCase) : BaseViewModel() {

    val goToProfileScreen = MutableLiveData<Boolean>()

    fun attempLogin(username: String?, password: String?) {

        super.loadingStatus.value = true

        super.subscriptions.add(userLogInUseCase.logInUser(username, password)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(this::onLoginSuccess, this::catchError))
    }

    private fun onLoginSuccess() {

        super.loadingStatus.value = false
        goToProfileScreen.value = true
    }

    private fun catchError(error: Throwable) {

        val msg = when(error) {
            is EmptyFieldException -> R.string.login_module_empty_field_error_msg
            is InvalidCredentialException -> R.string.login_module_invalid_credential_error_msg
            is WasNotAbleToSaveCredentialException -> R.string.login_module_cant_save_credential_error_msg
            else -> R.string.generic_error_message
        }

        super.loadingStatus.value = false
        super.showErrorMessage.value = msg
    }
}