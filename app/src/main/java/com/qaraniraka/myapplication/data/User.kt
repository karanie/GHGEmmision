package com.qaraniraka.myapplication.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    @PrimaryKey(autoGenerate = true)
    val userId: Int = 0,
    val email: String,
    val password: String,
    val fullName: String,
    val birthday: Int
)
