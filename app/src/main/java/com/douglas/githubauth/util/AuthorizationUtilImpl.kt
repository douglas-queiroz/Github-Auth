package com.douglas.githubauth.util

import android.util.Base64

class AuthorizationUtilImpl: AuthorizationUtil {

    override fun generateAuthorization(userName: String, password: String): String {

        val byteArrayCredentials = "$userName:$password".toByteArray()

        return Base64.encodeToString(byteArrayCredentials, Base64.NO_WRAP)
    }
}