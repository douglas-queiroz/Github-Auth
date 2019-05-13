package com.douglas.githubauth.data.local

import com.douglas.githubauth.domain.model.UserCredential

interface UserDao {

    fun saveUserCredentials(userCredential: UserCredential) : Boolean

    fun getUserCredential() : UserCredential
}