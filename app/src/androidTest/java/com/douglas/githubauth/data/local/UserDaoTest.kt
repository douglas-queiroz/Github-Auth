package com.douglas.githubauth.data.local

import android.support.test.InstrumentationRegistry
import com.douglas.githubauth.domain.model.UserCredential
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class UserDaoTest {

    private val username = "user_name"
    private val password = "password"
    private val userCredential = UserCredential(username, password)
    private lateinit var target: UserDao

    @Before
    fun setUp() {

        val appContext = InstrumentationRegistry.getTargetContext()
        target = UserDaoImpl(appContext)
    }

    @After
    fun tearDown() {

        target.removeUser()
    }

    @Test
    fun saveUserCredential() {

        val commit = target.saveUserCredential(userCredential)
        val credentialResult = target.getUserCredential()

        assertTrue(commit)
        assertNotNull(credentialResult)
        assertEquals(username, credentialResult?.userName)
        assertEquals(password, credentialResult?.password)
    }

    @Test
    fun getUserCredential() {

        val commit = target.saveUserCredential(userCredential)
        val credentialResult = target.getUserCredential()

        assertTrue(commit)
        assertNotNull(credentialResult)
        assertEquals(username, credentialResult?.userName)
        assertEquals(password, credentialResult?.password)
    }

    @Test
    fun removeUser() {

        val commit = target.saveUserCredential(userCredential)
        val commitRemove = target.removeUser()
        val credentialResult = target.getUserCredential()

        assertTrue(commit)
        assertTrue(commitRemove)
        assertNull(credentialResult)
    }
}