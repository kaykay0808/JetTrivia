package com.example.jettrivia.screens

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jettrivia.data.DataOrException
import com.example.jettrivia.model.QuestionItem
import com.example.jettrivia.repository.QuestionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuestionsViewModel @Inject constructor(private val repository: QuestionRepository) :
    ViewModel() {
    val data: MutableState<
            DataOrException<ArrayList<QuestionItem>,
                    Boolean,
                    Exception>> = mutableStateOf(DataOrException(null, true, Exception("")))

    init {
        getAllQuestion()
    }

    // We need to feed data with this function
    private fun getAllQuestion() {
        viewModelScope.launch {
            data.value.loading = true
            Log.d("TrueOrNot", "Boolean value: ${data.value.loading}")
            data.value = repository.getAllQuestions()
            if (data.value.data.toString().isNotEmpty()) {
                data.value.loading = false
                Log.d("TrueOrNot", "Boolean Value: ${data.value.loading}")
            }
        }
    }
    // Getting the total max question
    fun GetTotalQuestionSize() : Int {
        return data.value.data?.toMutableList()?.size!!
    }
}
