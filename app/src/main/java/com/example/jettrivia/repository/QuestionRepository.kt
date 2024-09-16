package com.example.jettrivia.repository

import android.util.Log
import com.example.jettrivia.data.DataOrException
import com.example.jettrivia.model.QuestionItem
import com.example.jettrivia.network.QuestionApi
import javax.inject.Inject

class QuestionRepository @Inject constructor(private val api: QuestionApi) {
    private val dataOrException =
        DataOrException<ArrayList<QuestionItem>, Boolean, Exception>()// we wrapped this inside of our custom DataOrException data class -> ArrayList<QuestionItem>(emptyList())
    // What more information do we need

    // Getting the question data
    suspend fun getAllQuestions(): DataOrException<ArrayList<QuestionItem>, Boolean, java.lang.Exception> {
        try {
            dataOrException.loading = true
            dataOrException.data = api.getAllQuestions()
            if (dataOrException.data.toString().isNotEmpty()) {dataOrException.loading = false}

        } catch (exception: Exception) {
            dataOrException.e = exception
            Log.d("Exc", "GetAllQuestions: ${dataOrException.e!!.localizedMessage}")
        }
        return dataOrException
    }
}
