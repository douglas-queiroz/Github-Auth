package com.douglas.githubauth.module.profile

import android.arch.lifecycle.MutableLiveData
import com.douglas.githubauth.R
import com.douglas.githubauth.domain.LoadUserUseCase
import com.douglas.githubauth.domain.UserLogOutUseCase
import com.douglas.githubauth.domain.exception.*
import com.douglas.githubauth.domain.model.User
import com.douglas.githubauth.module.base.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ProfileViewModel @Inject constructor(private val loadUserUseCase: LoadUserUseCase,
                                           private val logOutUseCase: UserLogOutUseCase) : BaseViewModel() {

    sealed class ViewState {
        class GoToLoginScreen : ViewState()
        class ShowProfile(val user: User) : ViewState()
    }

    val viewState = MutableLiveData<ViewState>()

    fun loadProfile() {

        loadingStatus.value = true

        subscriptions.add(loadUserUseCase.loadUser()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(this::loadProfileSuccess, this::catchError))
    }

    fun logout() {

        try {

            logOutUseCase.logOutUser()
            viewState.value = ViewState.GoToLoginScreen()

        } catch (error: Throwable) {
            catchError(error)
        }
    }

    private fun loadProfileSuccess(user: User) {

        loadingStatus.value = false
        viewState.value = ViewState.ShowProfile(user)
    }

    private fun catchError(error: Throwable) {

        when(error) {
            is HasNoUserLoggedIn -> {
                this.logout()
            }
            is InvalidCredentialException -> {
                this.logout()
            }
            is WasNotAbleToRemoveUser -> {
                val message = R.string.profile_module_couldnt_remove_user
                showErrorMessage.value = message
            }
            else -> R.string.generic_error_message
        }

        super.loadingStatus.value = false
    }
}