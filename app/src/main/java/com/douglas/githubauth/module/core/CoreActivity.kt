package com.douglas.githubauth.module.core

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.douglas.githubauth.R
import com.douglas.githubauth.module.base.BaseActivity

class CoreActivity : AppCompatActivity() {

    lateinit var viewModel: CoreViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_core)

        subscribeViewStates()
    }

    private fun subscribeViewStates() {

        viewModel.viewState.observe(this, Observer { viewState ->

            updateView(viewState)
        })
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
