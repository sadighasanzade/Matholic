package com.example.matholic.repo

import androidx.lifecycle.LiveData
import com.example.matholic.db.QuestionDAO
import com.example.matholic.model.Question
import com.example.matholic.model.QuestionResponse
import com.example.matholic.network.RetrofitInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import java.io.File


class MainRepository{
    val networkController = RetrofitInstance.api
    lateinit var dbController: QuestionDAO


    suspend fun addQuestion(question: Question) = withContext(Dispatchers.IO) {
        dbController.addQuestion(question)
    }

    suspend fun getAllQuestions() : LiveData<List<Question>> {
        return dbController.getAllQuestions()
    }

    suspend fun postQuestion(file : File, uuid : String) : Response<QuestionResponse> {
        val filePart = MultipartBody.Part.createFormData(
            "image",
            file.name,
            RequestBody.create(MediaType.parse("image/*"), file)
        )

        val requestBody = RequestBody.create(MediaType.parse("text/plain"), uuid)
        return networkController.postQuestion(filePart, requestBody)
    }

    suspend fun updateQuestion(uuid: String, expression : String) {
        dbController.updateQuestion(uuid, expression)
    }
}