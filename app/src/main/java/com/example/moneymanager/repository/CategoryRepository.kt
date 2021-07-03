package com.example.moneymanager.repository

import com.example.moneymanager.MyApplication
import com.example.moneymanager.model.category.Category
import com.example.moneymanager.model.category.CategoryDao
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class CategoryRepository(private val categoryDao: CategoryDao) {

    var currentOutcomeCategories: Array<Category> = emptyArray()
    var currentIncomeCategories: Array<Category> = emptyArray()

    val currentIncomeCategoriesFlow = flow{
        while(true){
            MyApplication.currentUser?.let {
                emit(currentIncomeCategories)
            }
            delay(1000)
        }
    }

    val currentOutcomeCategoriesFlow = flow{
        while(true){
            MyApplication.currentUser?.let {
                emit(currentOutcomeCategories)
            }
            delay(1000)
        }
    }

    init {
        fetchData(Category.Income)
        fetchData(Category.Outcome)
    }

    private fun fetchData(type: Int){
        GlobalScope.launch {
            when(type) {
                Category.Income ->
                    currentIncomeCategories =
                        categoryDao.getByType(MyApplication.currentUser!!.id!!, Category.Income)
                Category.Outcome ->
                    currentOutcomeCategories =
                        categoryDao.getByType(MyApplication.currentUser!!.id!!, Category.Outcome)
            }
        }.start()
    }

    suspend fun insert(category: Category): Boolean{
        val res = categoryDao.insert(category)
        fetchData(category.type)
        return res
    }
    suspend fun update(category: Category): Boolean{
        val res = categoryDao.update(category)
        fetchData(category.type)
        return res
    }
    suspend fun delete(category: Category){
        categoryDao.delete(category)
        fetchData(category.type)
    }
}