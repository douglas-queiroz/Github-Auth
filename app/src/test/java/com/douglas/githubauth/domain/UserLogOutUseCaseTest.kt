package com.douglas.githubauth.domain

import com.douglas.githubauth.data.local.UserDao
import org.junit.Before

import org.junit.Assert.*
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class UserLogOutUseCaseTest {

    @Mock
    lateinit var userDao: UserDao

    private lateinit var target: UserLogOutUseCase

    @Before
    fun setUp() {

        MockitoAnnotations.initMocks(this)

        target = UserLogOutUseCaseImpl(userDao)
    }

    fun `When has no user logged in`() {

    }

    fun `When log out user works fine`() {

    }
}