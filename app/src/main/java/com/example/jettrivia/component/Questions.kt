package com.example.jettrivia.component

import android.util.Log
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import com.example.jettrivia.screens.QuestionsViewModel

@Composable
fun Questions(viewModel: QuestionsViewModel) {
    // The value is our Wrapper(DataOrException)
    val questions = viewModel.data.value.data?.toMutableList()
    // Log.d("SIZE", "Questions: ${questions?.size}")

    if (viewModel.data.value.loading == true) {
        // A Loading circle
        CircularProgressIndicator()
        Log.d("Loading", "Questions: ....Loading....${viewModel.data.value}")
    } else {
        Log.d("Loading", "Questions: ....Loading Complete ${viewModel.data.value}")
        questions?.forEach { questionItem ->
            Log.d("Result", "Questions: ${questionItem.question}")
        }
    }
}
