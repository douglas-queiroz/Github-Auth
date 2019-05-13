package com.douglas.githubauth.domain.model

data class User(val name: String,
                val company: String,
                val email: String,
                val bio: String,
                val avatarUrl: String)