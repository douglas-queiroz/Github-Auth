package com.douglas.githubauth.module.profile

import com.douglas.githubauth.domain.LoadUserUseCase
import com.douglas.githubauth.domain.UserLogOutUseCase
import com.douglas.githubauth.module.base.BaseViewModel
import javax.inject.Inject

class ProfileViewModel @Inject constructor(private val loadUserUseCase: LoadUserUseCase,
                                           private val logOutUseCase: UserLogOutUseCase) : BaseViewModel() {

    fun loadProfile() {

    }

    fun logout() {

    }
}