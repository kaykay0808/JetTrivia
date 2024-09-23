package com.example.jettrivia.network

import com.example.jettrivia.model.QuestionModel
import retrofit2.http.GET

// https://raw.githubusercontent.com/itmmckernan/triviaJSON/master/world.json
interface QuestionApi {
    @GET("world.json")
    suspend fun getAllQuestions(): QuestionModel
}