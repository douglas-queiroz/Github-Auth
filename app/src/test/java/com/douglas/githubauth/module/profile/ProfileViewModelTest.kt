package com.douglas.githubauth.module.profile

import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.douglas.githubauth.RxImmediateSchedulerRule
import com.douglas.githubauth.TestObserver
import com.douglas.githubauth.domain.LoadUserUseCase
import com.douglas.githubauth.domain.UserLogOutUseCase
import com.douglas.githubauth.testObserver
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class ProfileViewModelTest {

    @get:Rule
    val taskExecutorRule = InstantTaskExecutorRule()

    @Rule
    @JvmField var testSchedulerRule = RxImmediateSchedulerRule()

    @Mock
    lateinit var loadUserUseCase: LoadUserUseCase

    @Mock
    lateinit var logOutUseCase: UserLogOutUseCase

    private lateinit var target: ProfileViewModel
    private lateinit var loadingStatusStore: TestObserver<Boolean>
    private lateinit var showMessageStatusStore: TestObserver<Int>


    @Before
    fun setUp() {

        MockitoAnnotations.initMocks(this)

        target = ProfileViewModel(loadUserUseCase, logOutUseCase)
        loadingStatusStore = target.loadingStatus.testObserver()
        showMessageStatusStore = target.showErrorMessage.testObserver()
    }

    @Test
    fun `When loadUserUseCase throws HasNoUserLoggedIn`() {

    }

    @Test
    fun `When loadUserUseCase throws InvalidCredentialException`() {

    }

    @Test
    fun `When loadProfile works fine`() {

    }

    @Test
    fun `When logOutUseCase throws WasNotAbleToRemoveUser`() {

    }

    @Test
    fun `When logout works fine`() {

    }
}