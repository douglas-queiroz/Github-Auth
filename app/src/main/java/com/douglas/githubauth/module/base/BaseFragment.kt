package com.douglas.githubauth.module.base

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import javax.inject.Inject

abstract class BaseFragment(@LayoutRes private val layout: Int): Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by lazy { getViewMode() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycle.addObserver(viewModel)
        subscribeViewModel()
    }

    private fun subscribeViewModel() {

        viewModel.loadingStatus.observe(this, Observer { showLoading ->
            showLoading(showLoading)
        })

        viewModel.showErrorMessage.observe(this, Observer { msg ->
            showErrorMessage(msg)
        })
    }

    private fun showErrorMessage(msg: Int?) {

    }

    private fun showLoading(showLoading: Boolean?) {

    }

    abstract fun getViewMode() : BaseViewModel
}