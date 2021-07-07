package com.example.moneymanager

import android.app.Application
import com.example.moneymanager.model.category.CategoryDatabase
import com.example.moneymanager.model.subcategory.SubcategoryDatabase
import com.example.moneymanager.repository.UserRepository
import com.example.moneymanager.model.user.User
import com.example.moneymanager.model.user.UserDatabase
import com.example.moneymanager.repository.CategoryRepository
import com.example.moneymanager.repository.SubcategoryRepository

class MyApplication : Application() {

    // Using by lazy so the database and the repository are only created when they're needed
    // rather than when the application starts

    // Declare databases
    private val userDatabase by lazy { UserDatabase.getInstance(this) }
    private val categoryDatabase by lazy { CategoryDatabase.getInstance(this) }
    private val subcategoryDatabase by lazy { SubcategoryDatabase.getInstance(this) }

    // Declare repositories
    val userRepository by lazy { UserRepository(userDatabase.getDao()) }
    val categoryRepository by lazy { CategoryRepository(categoryDatabase.getDao()) }
    val subcategoryRepository by lazy { SubcategoryRepository(subcategoryDatabase.getDao()) }

    companion object{

        // Database config
        const val databaseName = "money_manager1.db"
    }
}