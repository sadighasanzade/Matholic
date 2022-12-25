package com.example.matholic.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.matholic.db.QuestionDb
import com.example.matholic.model.Question
import com.example.matholic.model.QuestionResponse
import com.example.matholic.repo.MainRepository
import kotlinx.coroutines.launch
import okhttp3.Response
import java.io.File

class MainActivityViewModel(private val repository: MainRepository, application: Application) : AndroidViewModel(application) {
    lateinit var questions: LiveData<List<Question>>
    var response : MutableLiveData<retrofit2.Response <QuestionResponse> >  = MutableLiveData()
    init {
        val questionDAO= QuestionDb.getDatabase(application).contactDao()
        repository.dbController = questionDAO
    }

    fun saveQuestion(question: Question)  {
        viewModelScope.launch {
            repository.addQuestion(question)
        }
    }

    fun getAllQuestions(){
        viewModelScope.launch {
            questions = repository.getAllQuestions()
        }
    }

    fun postQuestion(file : File, uuid : String) {
        viewModelScope.launch {
            try{
                response.value = repository.postQuestion(file, uuid)
            }catch (ex : Exception) {
                Log.d("sado", "FAILED " + ex.toString())
            }

        }
    }

    fun updateQuestion(uuid : String, expression : String) {
        viewModelScope.launch {
            repository.updateQuestion(uuid, expression)
        }
    }

    fun updateBaseUrl() {
        viewModelScope.launch {
            repository.updateBaseUrl()
        }
    }

}