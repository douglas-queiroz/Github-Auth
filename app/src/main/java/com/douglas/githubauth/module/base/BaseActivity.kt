package com.douglas.githubauth.module.base

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.douglas.githubauth.R

abstract class BaseActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_core)
    }
}