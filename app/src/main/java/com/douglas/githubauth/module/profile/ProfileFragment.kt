package com.douglas.githubauth.module.profile

import android.os.Bundle
import com.douglas.githubauth.R
import com.douglas.githubauth.module.base.BaseFragment
import com.douglas.githubauth.module.base.BaseViewModel

class ProfileFragment : BaseFragment(R.layout.fragment_profile) {

    private lateinit var viewModel: ProfileViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun getViewMode(): BaseViewModel {
        return viewModel
    }
}
