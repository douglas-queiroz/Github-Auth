package com.douglas.githubauth.module.core

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.douglas.githubauth.Application
import com.douglas.githubauth.R
import com.douglas.githubauth.module.login.LoginFragment
import javax.inject.Inject

class CoreActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: CoreViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_core)

        initDependence()
        subscribeViewStates()
    }

    private fun initDependence() {

        Application.component.inject(this)
        viewModel = getViewModel()
    }

    private fun getViewModel() : CoreViewModel {

        return ViewModelProviders
            .of(this, viewModelFactory)
            .get(CoreViewModel::class.java)
    }

    private fun subscribeViewStates() {

        viewModel.viewState.observe(this, Observer { viewState ->

            updateView(viewState)
        })
    }

    override fun onStart() {
        super.onStart()

        viewModel.checkIfHasUserLoggedIn()
    }

    private fun updateView(viewState: CoreViewModel.ViewState?) {

        when(viewState) {
            is CoreViewModel.ViewState.ShowLoginScreen -> showLoginScreen()
            is CoreViewModel.ViewState.ShowProfileScreen -> showProfileScree()
        }
    }

    private fun showLoginScreen() {

        val loginFragment = LoginFragment()

        supportFragmentManager.beginTransaction()
            .replace(R.id.content, loginFragment)
            .commit()
    }

    private fun showProfileScree() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}
