package com.example.matholic.network

import com.example.matholic.model.Question
import com.example.matholic.model.QuestionResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface MatholicApi {

    @Multipart
    @POST("api/upload/")
    suspend fun postQuestion(
        @Part image : MultipartBody.Part,
        @Part("uuid") uuid : RequestBody
    ) : Response<QuestionResponse>
}