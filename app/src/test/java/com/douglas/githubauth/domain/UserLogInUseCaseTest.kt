package com.douglas.githubauth.domain

import com.douglas.githubauth.data.local.UserDao
import com.douglas.githubauth.data.remote.UserService
import com.douglas.githubauth.domain.exception.EmptyFieldException
import com.douglas.githubauth.domain.exception.InvalidCredentialException
import com.douglas.githubauth.domain.exception.WasNotAbleToSaveCredentialException
import com.douglas.githubauth.domain.model.UserCredential
import com.douglas.githubauth.util.AuthorizationUtil
import io.reactivex.Completable
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import retrofit2.HttpException


class UserLogInUseCaseTest {

    @Mock
    lateinit var userDao: UserDao

    @Mock
    lateinit var userService: UserService

    @Mock
    lateinit var authorizationUtil: AuthorizationUtil

    private lateinit var target: UserLogInUseCase

    private val userName = "user_name"
    private val password = "password"
    private val authorization = "authorization"
    private val userCredential = UserCredential(userName, password)

    @Before
    fun setUp() {

        MockitoAnnotations.initMocks(this)
        target = UserLogInUseCaseImpl(userDao, userService, authorizationUtil)
    }

    @Test
    fun `When username is empty`() {

        val testObserver = target.logInUser("", password).test()

        testObserver.assertNotCompleted()
        testObserver.assertError(EmptyFieldException::class.java)

        verify(userService, never()).checkCredentials(authorization)
        verify(userDao, never()).saveUserCredential(userCredential)
    }

    @Test
    fun `When username is null`() {

        val testObserver = target.logInUser(null, password).test()

        testObserver.assertNotCompleted()
        testObserver.assertError(EmptyFieldException::class.java)

        verify(userService, never()).checkCredentials(authorization)
        verify(userDao, never()).saveUserCredential(userCredential)
    }

    @Test
    fun `When password is empty`() {

        val testObserver = target.logInUser(userName, "").test()

        testObserver.assertNotCompleted()
        testObserver.assertError(EmptyFieldException::class.java)

        verify(userService, never()).checkCredentials(authorization)
        verify(userDao, never()).saveUserCredential(userCredential)
    }

    @Test
    fun `When password is null`() {

        val testObserver = target.logInUser(userName, null).test()

        testObserver.assertNotCompleted()
        testObserver.assertError(EmptyFieldException::class.java)

        verify(userService, never()).checkCredentials(authorization)
        verify(userDao, never()).saveUserCredential(userCredential)
    }

    @Test
    fun `When it isn't a valid credential`() {

        val httpError = mock(HttpException::class.java)

        `when`(httpError.code()).thenReturn(401)
        `when`(userService.checkCredentials(anyString())).thenReturn(Completable.error(httpError))
        `when`(authorizationUtil.generateAuthorization(userName, password)).thenReturn(authorization)

        val testObserver = target.logInUser(userName, password).test()

        testObserver.assertNotCompleted()
        testObserver.assertError(InvalidCredentialException::class.java)

        verify(userService, times(1)).checkCredentials(authorization)
        verify(userDao, never()).saveUserCredential(userCredential)
    }

    @Test
    fun `When couldn't save user credential `() {

        `when`(userService.checkCredentials(anyString())).thenReturn(Completable.complete())
        `when`(userDao.saveUserCredential(userCredential)).thenReturn(false)
        `when`(authorizationUtil.generateAuthorization(userName, password)).thenReturn(authorization)

        val testObserver = target.logInUser(userName, password).test()

        testObserver.assertNotCompleted()
        testObserver.assertError(WasNotAbleToSaveCredentialException::class.java)

        verify(userService, times(1)).checkCredentials(authorization)
        verify(userDao, times(1)).saveUserCredential(userCredential)
    }

    @Test
    fun `When it works fine`() {

        `when`(userService.checkCredentials(anyString())).thenReturn(Completable.complete())
        `when`(userDao.saveUserCredential(userCredential)).thenReturn(true)
        `when`(authorizationUtil.generateAuthorization(userName, password)).thenReturn(authorization)

        val testObserver = target.logInUser(userName, password).test()

        testObserver.assertCompleted()
        testObserver.assertNoErrors()

        verify(userService, times(1)).checkCredentials(authorization)
        verify(userDao, times(1)).saveUserCredential(userCredential)
    }
}