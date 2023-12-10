package com.qaraniraka.myapplication.data

import kotlinx.coroutines.flow.Flow

class OfflineUsersRepository(private val userDao: UserDao) : UsersRepository {
    override fun getUserByUserId(id: Int): Flow<List<User>> = userDao.getUserByUserId(id)

    override fun getUserByEmail(email: String): Flow<List<User>> = userDao.getUserByEmail(email)

    override fun getUserByEmailAndPassword(email: String, password: String) =
        userDao.getUserByEmailAndPassword(email, password)

    override suspend fun insertUser(user: User) = userDao.insert(user)

    override suspend fun deleteUser(user: User) = userDao.delete(user)

    override suspend fun updateUser(user: User) = userDao.update(user)
}