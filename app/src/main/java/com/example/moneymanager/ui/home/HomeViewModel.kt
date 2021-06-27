package com.example.moneymanager.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.moneymanager.repository.UserRepository

class HomeViewModel(userRepository: UserRepository) : ViewModel() {

    val user = userRepository.currentUserFlow.asLiveData()
}