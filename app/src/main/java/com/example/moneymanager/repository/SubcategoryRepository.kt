package com.example.moneymanager.repository

import com.example.moneymanager.model.category.Category
import com.example.moneymanager.model.subcategory.Subcategory
import com.example.moneymanager.model.subcategory.SubcategoryDao
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class SubcategoryRepository(private val subcategoryDao: SubcategoryDao) {

    val currentIncomeSubcategoriesFlow = MutableStateFlow{
        emptyArray<Subcategory>()
    }
    val currentOutcomeSubcategoriesFlow = MutableStateFlow{
        emptyArray<Subcategory>()
    }

    fun fetchData(type: Int){
        GlobalScope.launch {
            when(type){
                Category.Income -> {
                    if(CategoryRepository.currentOutcomeCategory != null) {
                        val incomeSubcategories = subcategoryDao.getByCategory(
                            UserRepository.currentUser!!.id!!,
                            CategoryRepository.currentIncomeCategory!!.id!!
                        )
                        currentIncomeSubcategoriesFlow.emit { incomeSubcategories }
                    }else{
                        currentIncomeSubcategoriesFlow.emit { emptyArray() }
                    }
                }
                Category.Outcome -> {
                    if(CategoryRepository.currentOutcomeCategory != null) {
                        val outcomeSubcategories = subcategoryDao.getByCategory(
                            UserRepository.currentUser!!.id!!,
                            CategoryRepository.currentOutcomeCategory!!.id!!
                        )
                        currentOutcomeSubcategoriesFlow.emit { outcomeSubcategories }
                    }else{
                        currentOutcomeSubcategoriesFlow.emit { emptyArray() }
                    }
                }
            }
        }
    }

    suspend fun insert(subcategory: Subcategory): Boolean{
        val res = subcategoryDao.insert(subcategory)
        val category = subcategoryDao.getCategoryById(subcategory.categoryId)
        fetchData(category.type)
        return res
    }
    suspend fun update(subcategory: Subcategory): Boolean{
        val res = subcategoryDao.update(subcategory)
        val category = subcategoryDao.getCategoryById(subcategory.categoryId)
        fetchData(category.type)
        return res
    }
    suspend fun delete(subcategory: Subcategory){
        subcategoryDao.delete(subcategory)
        val category = subcategoryDao.getCategoryById(subcategory.categoryId)
        fetchData(category.type)
    }
}