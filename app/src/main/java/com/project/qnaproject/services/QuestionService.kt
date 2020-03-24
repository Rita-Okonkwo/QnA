package com.project.qnaproject.services

import qa
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface QuestionService {
    @GET("quizes")
    fun getQuestionsAndAnswers(): Call<qa>

    companion object Factory {
        fun create(): QuestionService {
            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://quizzies.herokuapp.com/")
                .build()

            return retrofit.create(QuestionService::class.java)
        }
    }
}