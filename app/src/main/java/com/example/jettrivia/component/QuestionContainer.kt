package com.example.jettrivia.component

import android.util.Log
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.PathEffect
import com.example.jettrivia.screens.QuestionsViewModel

// Todo: make the score decrement when making the wrong choice
@Composable
fun QuestionContainer(viewModel: QuestionsViewModel) {
    val questions = viewModel.data.value.data?.toMutableList()
    // Log.d("SIZE", "Questions: ${questions?.size}")

    // change to by instead of = so we can take the .value away
    val questionIndex = remember {
        mutableIntStateOf(0)
    }
    // Moving states here
    val pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f))
    val progressFactor by remember(questionIndex.intValue) { mutableFloatStateOf(questionIndex.intValue * 0.005f) }

    // Todo: Move this logic to the viewmodel
    if (viewModel.data.value.loading == true) {
        // A Loading circle
        CircularProgressIndicator()
        Log.d("Loading", "Questions: ....Loading....${viewModel.data.value}")
    } else {
        val question = try {
            questions?.get(questionIndex.intValue)
        } catch (ex: Exception) {
            null
        }
        Log.d("Loading", "Questions: ....Loading Complete ${viewModel.data.value}")
        if (questions != null) {
            val choicesState = remember(question) { question?.choices?.toMutableList()!! }
            val answerIndexChoiceState = remember(question) { mutableStateOf<Int?>(null) }
            val correctAnswerState = remember(question) { mutableStateOf<Boolean?>(null) }
            val updateAnswer: (Int) -> Unit = remember(question) {
                {
                    // Check for the correct choice
                    // Answer state is set to the clicked Int/index (userAnswerState?)
                    // so if the index has the same value as the question.answer it will return false or true
                    answerIndexChoiceState.value = it
                    // it just mean the specific item in a list?
                    // correctAnswerState is just a boolean trigger
                    if (question != null) {
                        correctAnswerState.value = choicesState[it] == question.answer
                    }
                }
            }

            QuestionDisplay(
                question = question!!,
                questionIndex = questionIndex,
                pathEffect = pathEffect,
                viewModel = viewModel,
                choicesState = choicesState,
                answerIndexChoiceState = answerIndexChoiceState,
                correctAnswerState = correctAnswerState,
                progressFactor = progressFactor,
                updateAnswer = updateAnswer
            ) {
                questionIndex.intValue += 1
            }
        }
    }
}
