package com.douglas.githubauth.data.local

import com.douglas.githubauth.domain.model.UserCredencial

interface UserDao {

    fun saveUserCredencials(userCredential: UserCredencial) : Boolean
}