package com.example.moneymanager

import android.app.Application
import com.example.moneymanager.repository.UserRepository
import com.example.moneymanager.model.user.User
import com.example.moneymanager.model.user.UserDatabase

class MyApplication : Application() {

    // Using by lazy so the database and the repository are only created when they're needed
    // rather than when the application starts

    // Declare databases
    private val userDatabase by lazy { UserDatabase.getInstance(this) }

    // Declare repositories
    val userRepository by lazy { UserRepository(userDatabase.getDao()) }


    companion object{

        // Current user
        var currentUser: User? = null

        // Database config
        val databaseName = "money_manager.db"
    }
}