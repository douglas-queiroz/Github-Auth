package com.douglas.githubauth.util

interface AuthorizationUtil {

    fun generateAuthorization(userName: String, password: String): String
}