package com.douglas.githubauth.module.base

import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable

abstract class BaseViewModel: ViewModel(), LifecycleObserver {

    val subscriptions = CompositeDisposable()
    val loadingStatus = MutableLiveData<Boolean>()

    override fun onCleared() {
        super.onCleared()

        subscriptions.clear()
    }
}