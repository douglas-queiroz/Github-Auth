package com.douglas.githubauth.domain

import com.douglas.githubauth.helper.UserSessionHelper
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class CheckLoginUseCaseTest {

    @Mock
    lateinit var userSessionHelper: UserSessionHelper

    private lateinit var target: CheckLoginUseCase

    @Before
    fun setUp() {

        MockitoAnnotations.initMocks(this)
        target = CheckLoginUseCaseImpl(userSessionHelper)
    }

    @Test
    fun `When getUserToken returns an empty String`() {

        `when`(userSessionHelper.getUserToken()).thenReturn("")

        val result = target.hasUserLogged()

        Assert.assertFalse(result)
    }

    @Test
    fun `When getUserToken returns null`() {

        `when`(userSessionHelper.getUserToken()).thenReturn("")

        val result = target.hasUserLogged()

        Assert.assertFalse(result)
    }

    @Test
    fun `When getUserToken returns an token`() {

        `when`(userSessionHelper.getUserToken()).thenReturn("token")

        val result = target.hasUserLogged()

        Assert.assertTrue(result)
    }
}