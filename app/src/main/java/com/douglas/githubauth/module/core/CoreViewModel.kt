package com.douglas.githubauth.module.core

import android.arch.lifecycle.MutableLiveData
import com.douglas.githubauth.domain.CheckLoginUseCase
import com.douglas.githubauth.module.base.BaseViewModel
import javax.inject.Inject

class CoreViewModel
    @Inject constructor(private var checkLoginUseCase: CheckLoginUseCase): BaseViewModel() {

    sealed class ViewState {
        class ShowLoginScreen: ViewState()
        class ShowProfileScreen: ViewState()
    }

    val viewState = MutableLiveData<ViewState>()

    fun checkIfHasUserLoggedIn() {

        if (checkLoginUseCase.hasUserLogged()) {

            viewState.value = ViewState.ShowProfileScreen()
        } else {

            viewState.value = ViewState.ShowLoginScreen()
        }
    }
}