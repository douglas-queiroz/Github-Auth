package com.douglas.githubauth.module.login

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.View
import com.douglas.githubauth.Application
import com.douglas.githubauth.R
import com.douglas.githubauth.module.base.BaseFragment
import com.douglas.githubauth.module.base.BaseViewModel
import com.douglas.githubauth.module.profile.ProfileFragment
import kotlinx.android.synthetic.main.fragment_login.*


class LoginFragment : BaseFragment(R.layout.fragment_login) {

    private lateinit var viewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initDependence()
        subscribeViewModel()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
    }

    private fun setupView() {
        loginButton.setOnClickListener { onAttemptLogin() }
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

        if (goToProfileScreen != null && goToProfileScreen) {

            val profileFragment = ProfileFragment()

            activity?.supportFragmentManager
                ?.beginTransaction()
                ?.replace(R.id.content, profileFragment)
                ?.commit()
        }
    }

    private fun onAttemptLogin() {

        val username = usernameEditText.text.toString()
        val password = passwordEditText.text.toString()

        viewModel.attempLogin(username, password)
    }
}
