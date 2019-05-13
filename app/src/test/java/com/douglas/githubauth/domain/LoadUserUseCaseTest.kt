package com.douglas.githubauth.domain

import com.douglas.githubauth.data.local.UserDao
import com.douglas.githubauth.data.remote.UserService
import com.douglas.githubauth.domain.exception.HasNoUserLoggedIn
import com.douglas.githubauth.domain.exception.InvalidCredentialException
import com.douglas.githubauth.domain.model.User
import com.douglas.githubauth.domain.model.UserCredential
import com.douglas.githubauth.util.AuthorizationUtil
import io.reactivex.Observable
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import retrofit2.HttpException

class LoadUserUseCaseTest {

    @Mock
    lateinit var userDao: UserDao

    @Mock
    lateinit var userService: UserService

    @Mock
    lateinit var authorizationUtil: AuthorizationUtil

    private lateinit var target: LoadUserUseCase

    private val authorization = "authorization"
    private val userName = "user_name"
    private val password = "password"
    private val userCredential = UserCredential(userName, password)
    private val user = mock(User::class.java)

    @Before
    fun setUp() {

        MockitoAnnotations.initMocks(this)

        target = LoadUserUseCaseImpl(userDao, userService, authorizationUtil)

        `when`(userDao.getUserCredential()).thenReturn(userCredential)
        `when`(authorizationUtil.generateAuthorization(userName, password)).thenReturn(authorization)
        `when`(userService.fetchUser(authorization)).thenReturn(Observable.just(user))
    }

    @Test
    fun `When userDao returns a null UserCredential`() {

        `when`(userDao.getUserCredential()).thenReturn(null)

        val testObserver = target.loadUser().test()

        testObserver.assertNotCompleted()
        testObserver.assertError(HasNoUserLoggedIn::class.java)

        verify(userService, never()).fetchUser(authorization)
        verify(authorizationUtil, never()).generateAuthorization(userName, password)
    }

    @Test
    fun `When userService returns 401 http error`() {

        val httpError = mock(HttpException::class.java)

        `when`(httpError.code()).thenReturn(401)
        `when`(userService.fetchUser(authorization)).thenReturn(Observable.error(httpError))

        val testObserver = target.loadUser().test()

        testObserver.assertNotCompleted()
        testObserver.assertError(InvalidCredentialException::class.java)

        verify(userDao, times(1)).getUserCredential()
        verify(userService, times(1)).fetchUser(authorization)
        verify(authorizationUtil, times(1)).generateAuthorization(userName, password)
    }

    @Test
    fun `When everything works fine`() {

        val testObserver = target.loadUser().test()

        testObserver.assertCompleted()
        testObserver.assertNoErrors()

        verify(authorizationUtil, times(1)).generateAuthorization(userName, password)
        verify(userService, times(1)).fetchUser(authorization)
        verify(authorizationUtil, times(1)).generateAuthorization(userName, password)
    }
}