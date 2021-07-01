package com.example.moneymanager.ui.categories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.moneymanager.MyApplication
import com.example.moneymanager.model.category.Category
import com.example.moneymanager.repository.CategoryRepository
import kotlinx.coroutines.launch

class CategoriesViewModel(
    private val categoryRepository: CategoryRepository
) : ViewModel() {
    val incomeCategories = categoryRepository.currentIncomeCategoriesFlow.asLiveData()
    val outcomeCategories = categoryRepository.currentOutcomeCategoriesFlow.asLiveData()

    fun insertIncomeCategory(title: String){
        val category = Category(MyApplication.currentUser!!.id!!, title, Category.Income)
        viewModelScope.launch {
            categoryRepository.insert(category)
        }
    }

    fun insertOutcomeCategory(title: String){
        val category = Category(MyApplication.currentUser!!.id!!, title, Category.Outcome)
        viewModelScope.launch {
            categoryRepository.insert(category)
        }
    }

    fun updateCategory(category: Category){
        viewModelScope.launch {
            categoryRepository.update(category)
        }
    }
    fun deleteCategory(category: Category){
        viewModelScope.launch {
            categoryRepository.delete(category)
        }
    }
}

class CategoriesViewModelFactory(
    private val categoryRepository: CategoryRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return CategoriesViewModel(categoryRepository) as T
    }
}