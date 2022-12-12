package com.example.matholic.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.matholic.repo.MainRepository

class MainActivityViewModelFactory(private val repository: MainRepository,val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainActivityViewModel(repository, application) as T
    }
}