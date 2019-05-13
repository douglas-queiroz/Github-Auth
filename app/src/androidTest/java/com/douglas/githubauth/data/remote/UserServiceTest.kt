package com.douglas.githubauth.data.remote

import android.support.test.runner.AndroidJUnit4
import com.douglas.githubauth.BuildConfig
import com.douglas.githubauth.util.AuthorizationUtilImpl
import io.reactivex.disposables.CompositeDisposable
import org.junit.After
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import java.util.concurrent.CountDownLatch

@RunWith(AndroidJUnit4::class)
class UserServiceTest {

    private val username = BuildConfig.GIT_USER_USERNAME
    private val password = BuildConfig.GIT_USER_PASSWORD
    private val authorizationUtil = AuthorizationUtilImpl()
    private val subscription = CompositeDisposable()

    @After
    fun tearDown() {
        subscription.clear()
    }

    @Test
    fun loadUser() {

        val signal = CountDownLatch(1)

        val credential = authorizationUtil.generateAuthorization(username, password)

        val userService = RetrofitCreator.createRetrofit().create(UserService::class.java)

        subscription.add(userService.fetchUser(credential)
            .subscribe({
                Assert.assertNotNull(it.name)
                Assert.assertNotNull(it.email)
                Assert.assertNotNull(it.company)
                Assert.assertNotNull(it.avatarUrl)
                Assert.assertNotNull(it.bio)

                signal.countDown()
            }, {
                Assert.fail()
                signal.countDown()
            }))

        signal.await()
    }

    @Test
    fun checkCredentials() {

        val signal = CountDownLatch(1)

        val credential = authorizationUtil.generateAuthorization(username, password)

        val userService = RetrofitCreator.createRetrofit().create(UserService::class.java)

        subscription.add(userService.checkCredentials(credential)
            .subscribe({
                signal.countDown()
            }, {
                Assert.fail()
                signal.countDown()
            }))

        signal.await()
    }
}