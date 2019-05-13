package com.douglas.githubauth.data.local

import android.content.Context
import com.douglas.githubauth.domain.model.UserCredential

class UserDaoImpl(private val context: Context): UserDao {

    companion object {
        const val USER_SHARED_PREFERENCE = "user_shared_preference"
        const val USER_USERNAME = "user_username"
        const val USER_PASSWORD = "user_password"
    }

    private val sharedPreference by lazy { context.getSharedPreferences(USER_SHARED_PREFERENCE, Context.MODE_PRIVATE) }

    override fun saveUserCredential(userCredential: UserCredential): Boolean {

        val editor = sharedPreference.edit()

        editor.putString(USER_USERNAME, userCredential.userName)
        editor.putString(USER_PASSWORD, userCredential.password)

        return editor.commit()
    }

    override fun getUserCredential(): UserCredential? {

        val username = sharedPreference.getString(USER_USERNAME, null)
        val password = sharedPreference.getString(USER_PASSWORD, null)

        return if (username != null && password != null) {

            UserCredential(username, password)
        } else {

            null
        }
    }

    override fun removeUser(): Boolean {

        val editor = sharedPreference.edit()

        editor.remove(USER_USERNAME)
        editor.remove(USER_PASSWORD)

        return editor.commit()
    }
}