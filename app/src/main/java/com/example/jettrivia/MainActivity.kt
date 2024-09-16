package com.example.jettrivia

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.jettrivia.screens.QuestionsViewModel
import com.example.jettrivia.ui.theme.JetTriviaTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            JetTriviaTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    TriviaHome()
                }
            }
        }
    }
}

@Composable
fun TriviaHome(viewModel: QuestionsViewModel = hiltViewModel()) {
    Questions(viewModel)
}

@Composable
fun Questions(viewModel: QuestionsViewModel) {
    // The value is our Wrapper(DataOrException)
    val questions = viewModel.data.value.data?.toMutableList()
    // Log.d("SIZE", "Questions: ${questions?.size}")

    if (viewModel.data.value.loading == true) {
        Log.d("Loading", "Questions: ....Loading....${viewModel.data.value}")
    } else {
        Log.d("Loading", "Questions: ....Loading Complete ${viewModel.data.value}")
        questions?.forEach { questionItem ->
            Log.d("Result", "Questions: ${questionItem.question}")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    JetTriviaTheme {

    }
}