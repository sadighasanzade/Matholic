package com.example.matholic.repo

import androidx.lifecycle.LiveData
import com.example.matholic.db.QuestionDAO
import com.example.matholic.model.Question
import com.example.matholic.network.RetrofitInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MainRepository{
    val networkController = RetrofitInstance.api
    lateinit var dbController: QuestionDAO


    suspend fun addQuestion(question: Question) = withContext(Dispatchers.IO) {
        dbController.addQuestion(question)
    }

    suspend fun getAllQuestions() : LiveData<List<Question>> {
        return dbController.getAllQuestions()
    }
}