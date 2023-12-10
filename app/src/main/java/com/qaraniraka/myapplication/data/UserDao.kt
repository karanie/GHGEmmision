package com.qaraniraka.myapplication.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Insert
    suspend fun insert(user: User)

    @Update
    suspend fun update(user: User)

    @Delete
    suspend fun delete(user: User)

    @Query("SELECT * FROM User WHERE userId = :id")
    fun getUserByUserId(id: Int): Flow<List<User>>

    @Query("SELECT * FROM User WHERE email = :email")
    fun getUserByEmail(email: String): Flow<List<User>>

    @Query("SELECT * FROM User WHERE email = :email AND password = :password")
    fun getUserByEmailAndPassword(email: String, password: String): Flow<List<User>>
}