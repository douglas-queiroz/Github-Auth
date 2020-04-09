package com.douglas.githubauth.module.repositoryList

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.View
import com.douglas.githubauth.Application
import com.douglas.githubauth.R
import com.douglas.githubauth.domain.model.Repository
import com.douglas.githubauth.module.base.BaseFragment
import com.douglas.githubauth.module.base.BaseViewModel
import com.douglas.githubauth.module.login.LoginFragment
import kotlinx.android.synthetic.main.fragment_repository_list.*

class RepositoryListFragment : BaseFragment(R.layout.fragment_repository_list) {

    private lateinit var viewModel: RepositoryListViewModel
    private val adapter = RepositoryAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initDependence()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        viewModel.loadProfile()
    }

    private fun initDependence() {

        Application.component.inject(this)
        viewModel = getViewModel()
        subscribeViewModel()

    }

    private fun initView() {
        repositoriesRecyclerView.adapter = adapter
        logoutButton.setOnClickListener { viewModel.logout() }
    }

    private fun getViewModel() : RepositoryListViewModel {
        return ViewModelProviders
            .of(this, viewModelFactory)
            .get(RepositoryListViewModel::class.java)
    }

    override fun getViewMode(): BaseViewModel {
        return viewModel
    }

    private fun subscribeViewModel() {
        viewModel.viewState.observe(this, Observer { viewState ->
            updateView(viewState)
        })
    }

    private fun updateView(viewState: RepositoryListViewModel.ViewState?) {
        when(viewState) {
            is RepositoryListViewModel.ViewState.GoToLoginScreen -> goToLoginScreen()
            is RepositoryListViewModel.ViewState.ShowRepositories -> showRepositories(viewState.repositories)
        }
    }

    private fun showRepositories(repositories: List<Repository>) {
        adapter.repositories = repositories
        adapter.notifyDataSetChanged()
    }

    private fun goToLoginScreen() {
        val loginFragment = LoginFragment()

        activity?.supportFragmentManager
            ?.beginTransaction()
            ?.replace(R.id.content, loginFragment)
            ?.commit()
    }
}
