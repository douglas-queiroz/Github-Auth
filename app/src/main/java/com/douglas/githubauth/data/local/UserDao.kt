package com.douglas.githubauth.data.local

import com.douglas.githubauth.domain.model.UserCredential

interface UserDao {

    fun saveUserCredencials(userCredential: UserCredential) : Boolean
}