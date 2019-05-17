package com.douglas.githubauth.module.login

import com.douglas.githubauth.domain.UserLogInUseCase
import com.douglas.githubauth.domain.UserLogOutUseCase
import org.junit.Before

import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class LoginViewModelTest {

    @Mock
    lateinit var userLoginUseCase: UserLogInUseCase

    lateinit var target: LoginViewModel

    @Before
    fun setUp() {

        MockitoAnnotations.initMocks(this)
        target = LoginViewModel(userLoginUseCase)
    }

    @Test
    fun `When attempt login receives EmptyFieldException` () {

    }

    @Test
    fun `When attempt login receives InvalidCredentialException` () {

    }

    @Test
    fun `When attempt login receives WasNotAbleToSaveCredentialException` () {

    }

    @Test
    fun `When attempt login works fine` () {

    }
}