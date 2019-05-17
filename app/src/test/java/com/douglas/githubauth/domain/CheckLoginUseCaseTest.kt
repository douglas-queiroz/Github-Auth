package com.douglas.githubauth.domain

import com.douglas.githubauth.data.local.UserDao
import com.douglas.githubauth.domain.model.UserCredential
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class CheckLoginUseCaseTest {

    @Mock
    lateinit var userDao: UserDao

    private lateinit var target: CheckLoginUseCase

    @Before
    fun setUp() {

        MockitoAnnotations.initMocks(this)
        target = CheckLoginUseCaseImpl(userDao)
    }

    @Test
    fun `When getUserToken returns null`() {

        `when`(userDao.getUserCredential()).thenReturn(null)

        val result = target.hasUserLogged()

        Assert.assertFalse(result)
    }

    @Test
    fun `When getUserToken returns an token`() {

        val credential = UserCredential("username", "password")

        `when`(userDao.getUserCredential()).thenReturn(credential)

        val result = target.hasUserLogged()

        Assert.assertTrue(result)
    }
}