package com.qaraniraka.myapplication.model

data class PostSuccess(
    val fieldCount: Int,
    val affectedRows: Int,
    val insertId: Int,
    val info: String,
    val serverStatus: Int,
    val warningStatus: Int
)

data class VerifySuccess(
    val verified: Boolean,
    val session: String
)