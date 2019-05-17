package com.douglas.githubauth.module.core

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.douglas.githubauth.Application
import com.douglas.githubauth.R
import javax.inject.Inject

class CoreActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: CoreViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_core)

        initDependences()
        subscribeViewStates()
    }

    private fun initDependences() {
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
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun showProfileScree() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}
