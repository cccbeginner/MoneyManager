package com.example.moneymanager.ui.categories

import androidx.lifecycle.*
import com.example.moneymanager.model.category.Category
import com.example.moneymanager.model.subcategory.Subcategory
import com.example.moneymanager.repository.CategoryRepository
import com.example.moneymanager.repository.SubcategoryRepository
import com.example.moneymanager.repository.UserRepository
import kotlinx.coroutines.launch

class OutcomeViewModel(
    userRepository: UserRepository,
    private val categoryRepository: CategoryRepository,
    private val subcategoryRepository: SubcategoryRepository,
) : ViewModel() {
    val user = userRepository.currentUserFlow.asLiveData()
    val categories = categoryRepository.currentOutcomeCategoriesFlow.asLiveData()
    val subcategories = subcategoryRepository.currentOutcomeSubcategoriesFlow.asLiveData()

    val insertResult = MutableLiveData<Boolean>()
    val updateResult = MutableLiveData<Boolean>()
    private var selectedCategory: Category? = null
    private var selectedSubcategory: Subcategory? = null
    
    fun selectCategory(category: Category?){
        selectedCategory = category
        CategoryRepository.currentOutcomeCategory = category
        subcategoryRepository.fetchData(Category.Outcome)
    }
    fun selectSubcategory(subcategory: Subcategory){
        selectedSubcategory = subcategory
    }

    fun insertCategory(title: String){
        val category = Category(UserRepository.currentUser!!.id!!, title, Category.Outcome)
        viewModelScope.launch {
            val res = categoryRepository.insert(category)
            insertResult.postValue(res)
        }
    }
    fun insertSubcategory(title: String){
        val subcategory = Subcategory(UserRepository.currentUser!!.id!!, selectedCategory!!.id!!, title)
        viewModelScope.launch {
            val res = subcategoryRepository.insert(subcategory)
            insertResult.postValue(res)
        }
    }
    fun updateCategory(title: String){
        selectedCategory!!.title = title
        viewModelScope.launch {
            val res = categoryRepository.update(selectedCategory!!)
            updateResult.postValue(res)
        }
    }
    fun updateSubcategory(title: String){
        selectedSubcategory!!.title = title
        viewModelScope.launch {
            val res = subcategoryRepository.update(selectedSubcategory!!)
            updateResult.postValue(res)
        }
    }
    fun deleteCategory(){
        viewModelScope.launch {
            categoryRepository.delete(selectedCategory!!)
            selectCategory(null)
        }
    }
    fun deleteSubcategory(){
        viewModelScope.launch {
            subcategoryRepository.delete(selectedSubcategory!!)
        }
    }
    fun fetchCategory(){
        categoryRepository.fetchData(Category.Outcome)
    }
}

class OutcomeViewModelFactory(
    private val userRepository: UserRepository,
    private val categoryRepository: CategoryRepository,
    private val subcategoryRepository: SubcategoryRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return OutcomeViewModel(userRepository, categoryRepository, subcategoryRepository) as T
    }
}