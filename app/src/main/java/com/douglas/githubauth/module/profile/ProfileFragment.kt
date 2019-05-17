package com.douglas.githubauth.module.profile

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import com.douglas.githubauth.Application
import com.douglas.githubauth.R
import com.douglas.githubauth.di.ViewModelFactory
import com.douglas.githubauth.domain.model.User
import com.douglas.githubauth.module.base.BaseFragment
import com.douglas.githubauth.module.base.BaseViewModel

class ProfileFragment : BaseFragment(R.layout.fragment_profile) {

    private lateinit var viewModel: ProfileViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initDependece()
    }

    private fun initDependece() {

        Application.component.inject(this)
        viewModel = getViewModel()
        subscribeViewModel()

    }

    private fun getViewModel() : ProfileViewModel {
        return ViewModelProviders
            .of(this, viewModelFactory)
            .get(ProfileViewModel::class.java)
    }

    override fun getViewMode(): BaseViewModel {
        return viewModel
    }

    private fun subscribeViewModel() {
        viewModel.viewState.observe(this, Observer { viewState ->
            updateView(viewState)
        })
    }

    private fun updateView(viewState: ProfileViewModel.ViewState?) {
        when(viewState) {
            is ProfileViewModel.ViewState.ShowProfile -> fillOutView(viewState.user)
            is ProfileViewModel.ViewState.GoToLoginScreen -> goToLoginScreen()
        }
    }

    private fun fillOutView(user: User) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun goToLoginScreen() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
