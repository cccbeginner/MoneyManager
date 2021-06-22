package com.example.moneymanager.ui.records

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class RecordsViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is Records Fragment"
    }
    val text: LiveData<String> = _text
}