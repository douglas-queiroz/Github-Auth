package com.douglas.githubauth.module.core

import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.douglas.githubauth.TestObserver
import com.douglas.githubauth.domain.CheckLoginUseCase
import com.douglas.githubauth.testObserver
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class CoreViewModelTest {

    @get:Rule
    val taskExecutorRule = InstantTaskExecutorRule()

    @Mock
    lateinit var checkLoginUseCase: CheckLoginUseCase

    private lateinit var target: CoreViewModel
    private lateinit var viewStateStore: TestObserver<CoreViewModel.ViewState>

    @Before
    fun setUp() {

        MockitoAnnotations.initMocks(this)

        target = CoreViewModel(checkLoginUseCase)
        viewStateStore = target.viewState.testObserver()
    }

    @Test
    fun `When has no user logged in`() {

        `when`(checkLoginUseCase.hasUserLogged()).thenReturn(false)

        target.checkIfHasUserLoggedIn()

        assertEquals(1, viewStateStore.observedValues.count())
        assert(viewStateStore.observedValues[0] is CoreViewModel.ViewState.ShowLoginScreen)
    }

    @Test
    fun `When has user logged in`() {

        `when`(checkLoginUseCase.hasUserLogged()).thenReturn(true)

        target.checkIfHasUserLoggedIn()

        assertEquals(1, viewStateStore.observedValues.count())
        assert(viewStateStore.observedValues[0] is CoreViewModel.ViewState.ShowProfileScreen)
    }
}