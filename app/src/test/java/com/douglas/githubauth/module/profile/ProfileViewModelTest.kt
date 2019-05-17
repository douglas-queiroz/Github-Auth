package com.douglas.githubauth.module.profile

import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.douglas.githubauth.R
import com.douglas.githubauth.RxImmediateSchedulerRule
import com.douglas.githubauth.TestObserver
import com.douglas.githubauth.domain.LoadUserUseCase
import com.douglas.githubauth.domain.UserLogOutUseCase
import com.douglas.githubauth.domain.exception.HasNoUserLoggedIn
import com.douglas.githubauth.domain.exception.InvalidCredentialException
import com.douglas.githubauth.domain.exception.WasNotAbleToRemoveUser
import com.douglas.githubauth.domain.model.User
import com.douglas.githubauth.testObserver
import io.reactivex.Observable
import org.junit.*
import org.mockito.Mock
import org.mockito.Mockito.*
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
    private lateinit var viewStateStore: TestObserver<ProfileViewModel.ViewState>

    private val user = mock(User::class.java)

    @Before
    fun setUp() {

        MockitoAnnotations.initMocks(this)

        target = ProfileViewModel(loadUserUseCase, logOutUseCase)
        loadingStatusStore = target.loadingStatus.testObserver()
        showMessageStatusStore = target.showErrorMessage.testObserver()
        viewStateStore = target.viewState.testObserver()
    }

    @After
    fun tearDown() {
        loadingStatusStore.observedValues.clear()
        showMessageStatusStore.observedValues.clear()
        viewStateStore.observedValues.clear()
    }

    @Test
    fun `When loadUserUseCase throws HasNoUserLoggedIn`() {

        `when`(loadUserUseCase.loadUser()).thenReturn(Observable.error(HasNoUserLoggedIn()))

        target.loadProfile()

        Assert.assertEquals(2, loadingStatusStore.observedValues.count())
        Assert.assertTrue(loadingStatusStore.observedValues[0] == true)
        Assert.assertTrue(loadingStatusStore.observedValues[1] == false)

        Assert.assertEquals(0, showMessageStatusStore.observedValues.count())

        Assert.assertEquals(1, viewStateStore.observedValues.count())
        Assert.assertTrue(viewStateStore.observedValues[0] is ProfileViewModel.ViewState.GoToLoginScreen)
    }

    @Test
    fun `When loadUserUseCase throws InvalidCredentialException`() {

        `when`(loadUserUseCase.loadUser()).thenReturn(Observable.error(InvalidCredentialException()))

        target.loadProfile()

        Assert.assertEquals(2, loadingStatusStore.observedValues.count())
        Assert.assertTrue(loadingStatusStore.observedValues[0] == true)
        Assert.assertTrue(loadingStatusStore.observedValues[1] == false)

        Assert.assertEquals(0, showMessageStatusStore.observedValues.count())

        Assert.assertEquals(1, viewStateStore.observedValues.count())
        Assert.assertTrue(viewStateStore.observedValues[0] is ProfileViewModel.ViewState.GoToLoginScreen)
    }

    @Test
    fun `When loadProfile works fine`() {

        `when`(loadUserUseCase.loadUser()).thenReturn(Observable.just(user))

        target.loadProfile()

        Assert.assertEquals(2, loadingStatusStore.observedValues.count())
        Assert.assertTrue(loadingStatusStore.observedValues[0] == true)
        Assert.assertTrue(loadingStatusStore.observedValues[1] == false)

        Assert.assertEquals(0, showMessageStatusStore.observedValues.count())

        Assert.assertEquals(1, viewStateStore.observedValues.count())
        Assert.assertTrue(viewStateStore.observedValues[0] is ProfileViewModel.ViewState.ShowProfile)
    }

    @Test
    fun `When logOutUseCase throws WasNotAbleToRemoveUser`() {

        doAnswer { throw WasNotAbleToRemoveUser() }.`when`(logOutUseCase).logOutUser()

        target.logout()

        Assert.assertEquals(1, loadingStatusStore.observedValues.count())

        Assert.assertEquals(1, showMessageStatusStore.observedValues.count())
        Assert.assertEquals(R.string.profile_module_couldnt_remove_user, showMessageStatusStore.observedValues[0])

        Assert.assertEquals(0, viewStateStore.observedValues.count())
    }

    @Test
    fun `When logout works fine`() {

        target.logout()

        Assert.assertEquals(0, loadingStatusStore.observedValues.count())

        Assert.assertEquals(0, showMessageStatusStore.observedValues.count())

        Assert.assertEquals(1, viewStateStore.observedValues.count())
        Assert.assertTrue(viewStateStore.observedValues[0] is ProfileViewModel.ViewState.GoToLoginScreen)
    }
}