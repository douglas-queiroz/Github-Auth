package com.douglas.githubauth.module.base

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.afollestad.materialdialogs.MaterialDialog
import com.douglas.githubauth.R
import javax.inject.Inject

abstract class BaseFragment(@LayoutRes private val layout: Int): Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by lazy { getViewMode() }

    private var loadingDialog: MaterialDialog? = null

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

        val activity = activity ?: return
        val message = msg ?: return

        MaterialDialog.Builder(activity)
            .title(R.string.generec_error_title)
            .content(message)
            .positiveText(R.string.generic_positive_button)
            .cancelable(false)
            .show()
    }

    private fun showLoading(showLoading: Boolean?) {

        val activity = activity ?: return
        val showLoading = showLoading ?: return

        if (showLoading) {
            loadingDialog = MaterialDialog.Builder(activity)
                .content(R.string.generic_loading_text)
                .progress(true, 0)
                .cancelable(false)
                .show()
        } else {
            loadingDialog?.dismiss()
        }
    }

    abstract fun getViewMode() : BaseViewModel
}