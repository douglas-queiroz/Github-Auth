package com.douglas.githubauth.domain.model

import com.google.gson.annotations.SerializedName

open class User(val name: String,
                val company: String,
                val email: String,
                val bio: String,
                @SerializedName("avatar_url")
                val avatarUrl: String)