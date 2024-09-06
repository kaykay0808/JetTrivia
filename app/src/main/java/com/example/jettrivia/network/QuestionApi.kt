package com.example.jettrivia.network

import com.example.jettrivia.model.Question
import retrofit2.http.GET

interface QuestionApi {
    @GET("world.json")
    suspend fun getAllQuestions(): Question
}