package com.example.moneymanager.repository

import com.example.moneymanager.MyApplication
import com.example.moneymanager.model.user.User
import com.example.moneymanager.model.user.UserDao
import kotlinx.coroutines.flow.flow

class UserRepository(private val userDao: UserDao) {

    val currentUserFlow = flow {
        while(true){
            MyApplication.currentUser?.let {
                emit(MyApplication.currentUser)
            }
            kotlinx.coroutines.delay(300)
        }
    }

    fun insert(user: User){
        userDao.insert(user)
    }

    fun update(user: User) {
        userDao.update(user)
    }

    fun delete(user: User){
        userDao.update(user)
    }

    fun getWithInsert(user: User): User {
        return userDao.getWithInsert(user)
    }
}