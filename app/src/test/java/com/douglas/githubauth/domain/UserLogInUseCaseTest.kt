package com.douglas.githubauth.domain

import com.douglas.githubauth.data.local.UserDao
import com.douglas.githubauth.data.remote.UserService
import com.douglas.githubauth.domain.exception.EmptyFieldException
import com.douglas.githubauth.domain.exception.InvalidCredentialException
import com.douglas.githubauth.domain.exception.WasNotAbleToSaveCredentialException
import com.douglas.githubauth.domain.model.UserCredential
import io.reactivex.Completable
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.mockito.MockitoAnnotations
import retrofit2.HttpException

class UserLogInUseCaseTest {

    @Mock
    lateinit var userDao: UserDao

    @Mock
    lateinit var userService: UserService

    private lateinit var target: UserLogInUseCase

    private val userName = "user_name"
    private val password = "password"
    private val authorization = ""
    private val userCredential = UserCredential(userName, password)

    @Before
    fun setUp() {

        MockitoAnnotations.initMocks(this)
        target = UserLogInUseCaseImpl(userDao, userService)
    }

    @Test
    fun `When username is empty`() {

        val testObserver = target.logInUser("", password).test()

        testObserver.assertNotComplete()
        testObserver.assertError(EmptyFieldException::class.java)
    }

    @Test
    fun `When username is null`() {

        val testObserver = target.logInUser(null, password).test()

        testObserver.assertNotComplete()
        testObserver.assertError(EmptyFieldException::class.java)
    }

    @Test
    fun `When password is empty`() {

        val testObserver = target.logInUser(userName, "").test()

        testObserver.assertNotComplete()
        testObserver.assertError(EmptyFieldException::class.java)
    }

    @Test
    fun `When password is null`() {

        val testObserver = target.logInUser(userName, null).test()

        testObserver.assertNotComplete()
        testObserver.assertError(EmptyFieldException::class.java)
    }

    @Test
    fun `When it isn't a valid credential`() {

        val httpError = mock(HttpException::class.java)

        `when`(httpError.code()).thenReturn(401)
        `when`(userService.checkCredentials(authorization)).thenReturn(Completable.error(httpError))

        val testObserver = target.logInUser(userName, password).test()

        testObserver.assertNotComplete()
        testObserver.assertError(InvalidCredentialException::class.java)
    }

    @Test
    fun `When couldn't save user credential `() {

        `when`(userService.checkCredentials(authorization)).thenReturn(Completable.complete())
        `when`(userDao.saveUserCredencials(userCredential)).thenReturn(false)

        val testObserver = target.logInUser(userName, password).test()

        testObserver.assertNotComplete()
        testObserver.assertError(WasNotAbleToSaveCredentialException::class.java)
    }

    @Test
    fun `When it works fine`() {

        `when`(userService.checkCredentials(authorization)).thenReturn(Completable.complete())
        `when`(userDao.saveUserCredencials(userCredential)).thenReturn(false)

        val testObserver = target.logInUser(userName, null).test()

        testObserver.assertComplete()
        testObserver.assertNoErrors()
    }
}