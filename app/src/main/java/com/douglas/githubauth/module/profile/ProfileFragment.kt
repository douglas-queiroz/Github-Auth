package com.douglas.githubauth.module.profile

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.View
import com.douglas.githubauth.Application
import com.douglas.githubauth.R
import com.douglas.githubauth.domain.model.User
import com.douglas.githubauth.module.base.BaseFragment
import com.douglas.githubauth.module.base.BaseViewModel
import com.douglas.githubauth.module.login.LoginFragment
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_profile.*

class ProfileFragment : BaseFragment(R.layout.fragment_profile) {

    private lateinit var viewModel: ProfileViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initDependece()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        viewModel.loadProfile()
    }

    private fun initDependece() {

        Application.component.inject(this)
        viewModel = getViewModel()
        subscribeViewModel()

    }

    private fun initView() {
        logoutButton.setOnClickListener { viewModel.logout() }
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

        Picasso.get()
            .load(user.avatarUrl)
            .into(avatarImageView)

        nameTextView.text = user.name

        emailTextView.text = user.email

        locationTextView.text = user.location
    }

    private fun goToLoginScreen() {
        val loginFragment = LoginFragment()

        activity?.supportFragmentManager
            ?.beginTransaction()
            ?.replace(R.id.content, loginFragment)
            ?.commit()
    }
}
