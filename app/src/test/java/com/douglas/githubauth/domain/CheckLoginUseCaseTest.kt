package com.douglas.githubauth.domain

import org.junit.Assert
import org.junit.Test

class CheckLoginUseCaseTest {

    private lateinit var target: CheckLoginUseCase

    @Test
    fun `When has no user logged`() {

        val result = target.hasUserLogged()

        Assert.assertFalse(result)
    }

    @Test
    fun `When has a user logged`() {

        val result = target.hasUserLogged()

        Assert.assertTrue(result)
    }
}