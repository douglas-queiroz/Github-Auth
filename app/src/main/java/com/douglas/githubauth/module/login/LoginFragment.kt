package com.douglas.githubauth.module.login

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.douglas.githubauth.Application
import com.douglas.githubauth.R
import com.douglas.githubauth.module.base.BaseFragment
import com.douglas.githubauth.module.base.BaseViewModel
import kotlinx.android.synthetic.main.fragment_login.*


class LoginFragment : BaseFragment(R.layout.fragment_login) {

    private lateinit var viewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initDependence()
        subscribeViewModel()
    }

    private fun initDependence() {

        Application.component.inject(this)
        viewModel = getViewModel()
    }

    override fun getViewMode(): BaseViewModel {
        return viewModel
    }

    private fun getViewModel(): LoginViewModel {

        return ViewModelProviders
            .of(this, viewModelFactory)
            .get(LoginViewModel::class.java)
    }

    private fun subscribeViewModel() {
        viewModel.goToProfileScreen.observe(this, Observer { goToProfileScreen ->
            gotToProfileScreen(goToProfileScreen)
        })
    }

    private fun gotToProfileScreen(goToProfileScreen: Boolean?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun onAttemptLogin() {

        val username = usernameEditText.text.toString()
        val password = passwordEditText.text.toString()

        viewModel.attempLogin(username, password)
    }
}
