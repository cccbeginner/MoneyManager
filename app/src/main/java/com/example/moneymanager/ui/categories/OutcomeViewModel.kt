package com.example.moneymanager.ui.categories

import androidx.lifecycle.*
import com.example.moneymanager.MyApplication
import com.example.moneymanager.model.category.Category
import com.example.moneymanager.repository.CategoryRepository
import kotlinx.coroutines.launch

class OutcomeViewModel(
    private val categoryRepository: CategoryRepository
) : ViewModel() {
    val categories = categoryRepository.currentOutcomeCategoriesFlow.asLiveData()

    val insertResult = MutableLiveData<Boolean>()
    val updateResult = MutableLiveData<Boolean>()
    private lateinit var selectedCategory: Category

    fun selectCategory(category: Category){
        selectedCategory = category
    }

    fun insertCategory(title: String){
        val category = Category(MyApplication.currentUser!!.id!!, title, Category.Outcome)
        viewModelScope.launch {
            val res = categoryRepository.insert(category)
            insertResult.postValue(res)
        }
    }

    fun updateCategory(title: String){
        selectedCategory.title = title
        viewModelScope.launch {
            val res = categoryRepository.update(selectedCategory)
            updateResult.postValue(res)
        }
    }
    fun deleteCategory(){
        viewModelScope.launch {
            categoryRepository.delete(selectedCategory)
        }
    }
}

class OutcomeViewModelFactory(
    private val categoryRepository: CategoryRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return OutcomeViewModel(categoryRepository) as T
    }
}