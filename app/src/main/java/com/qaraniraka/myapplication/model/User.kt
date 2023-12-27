package com.qaraniraka.myapplication.model

import com.google.gson.annotations.SerializedName

data class UserRegistrationPostData(
    val email: String,
    val password: String,
    @SerializedName("full_name")
    val fullName: String,
    val birthday: String
)

data class UserLoginPostData(
    val email: String,
    val password: String,
)

data class UserLogoutPostData(
    val session: String,
)

data class UserCheckEmailData(
    val email: String?
)