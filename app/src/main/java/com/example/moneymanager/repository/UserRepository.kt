package com.example.moneymanager.repository

import com.example.moneymanager.model.user.User
import com.example.moneymanager.model.user.UserDao
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow

class UserRepository(private val userDao: UserDao) {

    val currentUserFlow = MutableStateFlow<User?> (null)

    fun insert(user: User){
        userDao.insert(user)
    }

    fun update(user: User) {
        userDao.update(user)
    }

    fun delete(user: User){
        userDao.update(user)
    }

    suspend fun setCurrentUser(user: User){
        currentUserFlow.emit(user)
        currentUser = user
    }

    fun getWithInsert(user: User): User {
        return userDao.getWithInsert(user)
    }

    companion object{
        var currentUser: User? = null
    }
}