package com.loyo.giftapplication.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.SavedStateHandle

class HomeViewModel(private val savedStateHandle: SavedStateHandle) : ViewModel() {
    private val salaryKay = "salary_key"
    fun getSalary(): MutableLiveData<String> {
        if (!savedStateHandle.contains(salaryKay)) {
            savedStateHandle.set(salaryKay, "")
        }
        return savedStateHandle.getLiveData<String>(salaryKay)
    }
}