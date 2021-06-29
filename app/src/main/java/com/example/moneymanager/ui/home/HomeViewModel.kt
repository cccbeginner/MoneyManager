package com.example.moneymanager.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import com.example.moneymanager.repository.UserRepository

class HomeViewModel(userRepository: UserRepository) : ViewModel() {

    val user = userRepository.currentUserFlow.asLiveData()
}

class HomeViewModelFactory(private val userRepository: UserRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return HomeViewModel(userRepository) as T
    }
}