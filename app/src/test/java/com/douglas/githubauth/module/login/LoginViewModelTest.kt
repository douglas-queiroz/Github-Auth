package com.douglas.githubauth.module.login

import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.douglas.githubauth.R
import com.douglas.githubauth.RxImmediateSchedulerRule
import com.douglas.githubauth.TestObserver
import com.douglas.githubauth.domain.UserLogInUseCase
import com.douglas.githubauth.domain.UserLogOutUseCase
import com.douglas.githubauth.domain.exception.EmptyFieldException
import com.douglas.githubauth.domain.exception.InvalidCredentialException
import com.douglas.githubauth.domain.exception.WasNotAbleToSaveCredentialException
import com.douglas.githubauth.module.core.CoreViewModel
import com.douglas.githubauth.testObserver
import io.reactivex.Completable
import org.junit.Before

import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class LoginViewModelTest {

    @get:Rule
    val taskExecutorRule = InstantTaskExecutorRule()

    @Rule
    @JvmField var testSchedulerRule = RxImmediateSchedulerRule()

    @Mock
    lateinit var userLoginUseCase: UserLogInUseCase

    private lateinit var target: LoginViewModel
    private lateinit var loadingStatusStore: TestObserver<Boolean>
    private lateinit var showMessageStatusStore: TestObserver<Int>
    private lateinit var goToProfileScreenStatusStore: TestObserver<Boolean>

    private val userName = "username"
    private val password = "password"

    @Before
    fun setUp() {

        MockitoAnnotations.initMocks(this)

        target = LoginViewModel(userLoginUseCase)
        loadingStatusStore = target.loadingStatus.testObserver()
        showMessageStatusStore = target.showErrorMessage.testObserver()
        goToProfileScreenStatusStore = target.goToProfileScreen.testObserver()
    }

    @Test
    fun `When attempt login receives EmptyFieldException` () {

        `when`(userLoginUseCase.logInUser(null, null)).thenReturn(Completable.error(
            EmptyFieldException()
        ))

        target.attempLogin(null, null)

        assertEquals(2, loadingStatusStore.observedValues.count())
        assertTrue(loadingStatusStore.observedValues[0] == true)
        assertTrue(loadingStatusStore.observedValues[1] == false)

        assertEquals(1, showMessageStatusStore.observedValues.count())
        assertEquals(R.string.login_module_empty_field_error_msg, showMessageStatusStore.observedValues[0])

        assertEquals(0, goToProfileScreenStatusStore.observedValues.count())
    }

    @Test
    fun `When attempt login receives InvalidCredentialException` () {

        `when`(userLoginUseCase.logInUser(userName, password)).thenReturn(Completable.error(
            InvalidCredentialException()
        ))

        target.attempLogin(userName, password)

        assertEquals(2, loadingStatusStore.observedValues.count())
        assertTrue(loadingStatusStore.observedValues[0] == true)
        assertTrue(loadingStatusStore.observedValues[1] == false)

        assertEquals(1, showMessageStatusStore.observedValues.count())
        assertEquals(R.string.login_module_invalid_credential_error_msg, showMessageStatusStore.observedValues[0])

        assertEquals(0, goToProfileScreenStatusStore.observedValues.count())
    }

    @Test
    fun `When attempt login receives WasNotAbleToSaveCredentialException` () {

        `when`(userLoginUseCase.logInUser(userName, password)).thenReturn(Completable.error(
            WasNotAbleToSaveCredentialException()
        ))

        target.attempLogin(userName, password)

        assertEquals(2, loadingStatusStore.observedValues.count())
        assertTrue(loadingStatusStore.observedValues[0] == true)
        assertTrue(loadingStatusStore.observedValues[1] == false)

        assertEquals(1, showMessageStatusStore.observedValues.count())
        assertEquals(R.string.login_module_cant_save_credential_error_msg, showMessageStatusStore.observedValues[0])

        assertEquals(0, goToProfileScreenStatusStore.observedValues.count())
    }

    @Test
    fun `When attempt login works fine` () {

        `when`(userLoginUseCase.logInUser(userName, password)).thenReturn(Completable.complete())

        target.attempLogin(userName, password)

        assertEquals(2, loadingStatusStore.observedValues.count())
        assertTrue(loadingStatusStore.observedValues[0] == true)
        assertTrue(loadingStatusStore.observedValues[1] == false)

        assertEquals(0, showMessageStatusStore.observedValues.count())

        assertEquals(1, goToProfileScreenStatusStore.observedValues.count())
        assertTrue(goToProfileScreenStatusStore.observedValues[0] == true)
    }
}