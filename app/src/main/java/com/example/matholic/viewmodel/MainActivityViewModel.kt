package com.example.matholic.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.matholic.db.QuestionDb
import com.example.matholic.model.Question
import com.example.matholic.repo.MainRepository
import kotlinx.coroutines.launch

class MainActivityViewModel(private val repository: MainRepository, application: Application) : AndroidViewModel(application) {
    lateinit var questions: LiveData<List<Question>>
    init {
        val questionDAO= QuestionDb.getDatabase(application).contactDao()
        repository.dbController = questionDAO
    }

    fun saveQuestion(question: Question) =  {
        viewModelScope.launch {
            repository.addQuestion(question)
        }
    }

    fun getAllQuestions(){
        viewModelScope.launch {
            questions = repository.getAllQuestions()
        }
    }


}