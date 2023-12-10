package com.qaraniraka.myapplication.data

import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun getUserByUserId(id: Int): Flow<List<User>>
    fun getUserByEmail(email: String): Flow<List<User>>
    fun getUserByEmailAndPassword(email: String, password: String): Flow<List<User>>
    suspend fun insertUser(user: User)
    suspend fun deleteUser(user: User)
    suspend fun updateUser(user: User)
}