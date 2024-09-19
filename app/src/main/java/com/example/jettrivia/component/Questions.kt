package com.example.jettrivia.component

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jettrivia.model.QuestionItem
import com.example.jettrivia.screens.QuestionsViewModel
import com.example.jettrivia.util.AppColors

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
        if (questions != null) {
            QuestionDisplay(question = questions.first())
        }
    }
}

// @Preview
@Composable
fun QuestionDisplay(
    question: QuestionItem,
    // questionIndex: MutableState<Int>,
    // viewModel: QuestionsViewModel,
    onNextClicked: (Int) -> Unit = {}
) {
    val pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f))
    val choicesState = remember(question) { question.choices.toMutableList() }
    val answerState = remember(question) { mutableStateOf<Int?>(null) }
    val correctAnswerState = remember(question) { mutableStateOf<Boolean?>(null) }
    val updateAnswer: (Int) -> Unit = remember(question) {
        {
            answerState.value = it
            correctAnswerState.value = choicesState[it] == question.answer
        }
    }
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(4.dp),
        color = AppColors.mDarkPurple
    ) {
        // All of items are going to lay out vertically (Downwards)
        Column(
            modifier = Modifier
                .padding(12.dp),
            verticalArrangement = Arrangement.Top, // From top to bottom
            horizontalAlignment = Alignment.Start // From left to right
        ) {
            QuestionTracker()
            DrawDottedLine(pathEffect = pathEffect)
            // Question card
            Column {
                Text(
                    modifier = Modifier
                        .padding(6.dp)
                        .align(alignment = Alignment.Start)
                        .fillMaxHeight(0.3f), // 30% of the height
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    lineHeight = 22.sp,
                    color = AppColors.mOffWhite,
                    text = question.question
                )
                // Choices
                choicesState.forEachIndexed { index, answerText ->
                    Row(
                        modifier = Modifier
                            .padding(3.dp)
                            .fillMaxWidth()
                            .height(45.dp)
                            .border(
                                width = 4.dp,
                                brush = Brush.linearGradient(colors = listOf(AppColors.mOffDarkPurple, AppColors.mOffDarkPurple)),
                                shape = RoundedCornerShape(15.dp)
                            )
                            .clip(RoundedCornerShape(topStartPercent = 50, topEndPercent = 50, bottomEndPercent = 50, bottomStartPercent = 50))
                            .background(Color.Transparent),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // selected is checking if our choices is the same as the answer
                        RadioButton(
                            modifier = Modifier.padding(start = 16.dp),
                            selected = (answerState.value == index),
                            colors = RadioButtonDefaults
                                .colors(
                                    selectedColor = if (correctAnswerState.value == true && index == answerState.value) {
                                        Color.Green.copy(alpha = 0.2f)
                                    } else {
                                        Color.Red.copy(alpha = 0.2f)
                                    }
                                ),
                            onClick = {updateAnswer(index)}
                        )
                        Text(text = answerText)

                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun QuestionTracker(
    counter: Int = 10,
    total: Int = 100
) {
    Text(
        modifier = Modifier.padding(20.dp),
        text = buildAnnotatedString {
            withStyle(
                style = SpanStyle(
                    color = AppColors.mLightGray,
                    fontWeight = FontWeight.Bold,
                    fontSize = 27.sp
                )
            ) {
                append("Question $counter/")
            }
            withStyle(
                style = SpanStyle(
                    color = AppColors.mLightGray,
                    fontWeight = FontWeight.Light,
                    fontSize = 14.sp
                )
            ) {
                append("$total")
            }
        }
    )
}

@Composable
fun DrawDottedLine(
    pathEffect: PathEffect
) {
    androidx.compose.foundation.Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .height(1.dp),
        onDraw = {
            drawLine(
                color = AppColors.mLightGray,
                start = Offset(0f, 0f),
                end = Offset(size.width, 0f),
                pathEffect = pathEffect
            )
        }
    )
}


