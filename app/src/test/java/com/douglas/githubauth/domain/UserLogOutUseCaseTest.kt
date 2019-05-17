package com.douglas.githubauth.domain

import com.douglas.githubauth.data.local.UserDao
import com.douglas.githubauth.domain.exception.WasNotAbleToRemoveUser
import org.junit.Assert
import org.junit.Before
import org.junit.Test
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

    @Test
    fun `When user dao was not able to remove use`() {

        `when`(userDao.removeUser()).thenReturn(false)

        try {

            target.logOutUser()

            Assert.fail()

        } catch (error: Exception) {

            Assert.assertTrue(error is WasNotAbleToRemoveUser)
        }
    }

    @Test
    fun `When log out user works fine`() {

        `when`(userDao.removeUser()).thenReturn(true)

        target.logOutUser()

    }
}