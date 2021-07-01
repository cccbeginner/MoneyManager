package com.example.moneymanager.repository

import com.example.moneymanager.MyApplication
import com.example.moneymanager.model.category.Category
import com.example.moneymanager.model.category.CategoryDao
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow

class CategoryRepository(private val categoryDao: CategoryDao) {

    var currentOutcomeCategories: Array<Category> = emptyArray()
    var currentIncomeCategories: Array<Category> = emptyArray()

    val currentIncomeCategoriesFlow = flow{
        while(true){
            MyApplication.currentUser?.let {
                emit(currentIncomeCategories)
            }
            delay(500)
        }
    }

    val currentOutcomeCategoriesFlow = flow{
        while(true){
            MyApplication.currentUser?.let {
                emit(currentOutcomeCategories)
            }
            delay(500)
        }
    }

    private fun fetchData(type: Int){
        when(type) {
            Category.Income ->
                currentIncomeCategories =
                    categoryDao.getByType(MyApplication.currentUser!!.id!!, Category.Income)
            Category.Outcome ->
                currentOutcomeCategories =
                    categoryDao.getByType(MyApplication.currentUser!!.id!!, Category.Outcome)
        }
    }

    init {
        Thread {
            fetchData(Category.Income)
            fetchData(Category.Outcome)
        }.start()
    }

    suspend fun insert(category: Category){
        println(category)
        println(MyApplication.currentUser)
        categoryDao.insert(category)
        fetchData(category.type)
    }
    suspend fun update(category: Category){
        categoryDao.update(category)
        fetchData(category.type)
    }
    suspend fun delete(category: Category){
        categoryDao.delete(category)
        fetchData(category.type)
    }
}