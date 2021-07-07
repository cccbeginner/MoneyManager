package com.example.moneymanager.repository

import com.example.moneymanager.model.category.Category
import com.example.moneymanager.model.category.CategoryDao
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class CategoryRepository(private val categoryDao: CategoryDao) {

    val currentIncomeCategoriesFlow = MutableStateFlow{
        emptyArray<Category>()
    }
    val currentOutcomeCategoriesFlow = MutableStateFlow{
        emptyArray<Category>()
    }

    fun fetchData(type: Int){
        GlobalScope.launch {
            when(type) {
                Category.Income -> {
                    val incomeCategories =
                        categoryDao.getByType(UserRepository.currentUser!!.id!!, Category.Income)
                    currentIncomeCategoriesFlow.emit { incomeCategories }
                }
                Category.Outcome -> {
                    val outcomeCategories =
                        categoryDao.getByType(UserRepository.currentUser!!.id!!, Category.Outcome)
                    currentOutcomeCategoriesFlow.emit { outcomeCategories }
                }
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

    companion object{
        var currentIncomeCategory: Category? = null
        var currentOutcomeCategory: Category? = null
    }
}