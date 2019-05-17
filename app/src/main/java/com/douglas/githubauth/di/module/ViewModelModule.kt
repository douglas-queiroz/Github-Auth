package com.douglas.githubauth.di.module

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.douglas.githubauth.di.ViewModelFactory
import com.douglas.githubauth.di.ViewModelKey
import com.douglas.githubauth.module.core.CoreViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(CoreViewModel::class)
    internal abstract fun bindsCoreViewModel(coreViewModel: CoreViewModel) : ViewModel

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}