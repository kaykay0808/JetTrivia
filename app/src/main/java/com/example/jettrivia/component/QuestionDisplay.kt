package com.example.jettrivia.component

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults.buttonColors
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jettrivia.model.QuestionItem
import com.example.jettrivia.screens.QuestionsViewModel
import com.example.jettrivia.util.AppColors

@Composable
fun QuestionDisplay(
    question: QuestionItem,
    questionIndex: MutableState<Int>,
    viewModel: QuestionsViewModel,
    pathEffect: PathEffect,
    choicesState: List<String>,
    answerIndexChoiceState: MutableState<Int?>,
    correctAnswerState: MutableState<Boolean?>,
    progressFactor: Float,
    updateAnswer: (Int) -> Unit,
    onNextClicked: (Int) -> Unit,
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        color = AppColors.mDarkPurple
    ) {
        // All of items are going to lay out vertically (Downwards)
        Column(
            modifier = Modifier
                .padding(12.dp),
            verticalArrangement = Arrangement.Top, // From top to bottom
            horizontalAlignment = Alignment.Start // From left to right
        ) {
            if (questionIndex.value >= 3) {
                ShowProgress(
                    score = questionIndex.value,
                    progressFactor = progressFactor
                )
            }
            QuestionTracker(counter = questionIndex.value, total = viewModel.GetTotalQuestionSize())
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
                choicesState.forEachIndexed { index, answerAlternativeChoicesText ->
                    Row(
                        modifier = Modifier
                            .padding(3.dp)
                            .fillMaxWidth()
                            .height(45.dp)
                            .border(
                                width = 4.dp,
                                brush = Brush.linearGradient(
                                    colors = listOf(
                                        AppColors.mOffDarkPurple,
                                        AppColors.mOffDarkPurple
                                    )
                                ),
                                shape = RoundedCornerShape(15.dp)
                            )
                            .clip(
                                RoundedCornerShape(
                                    topStartPercent = 50,
                                    topEndPercent = 50,
                                    bottomEndPercent = 50,
                                    bottomStartPercent = 50
                                )
                            )
                            .background(Color.Transparent),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // selected is checking if our choices is the same as the answer
                        RadioButton(
                            modifier = Modifier.padding(start = 16.dp),
                            selected = (answerIndexChoiceState.value == index),
                            onClick = { updateAnswer(index) },
                            colors = RadioButtonDefaults
                                .colors(
                                    selectedColor = if (correctAnswerState.value == true && index == answerIndexChoiceState.value) {
                                        Color.Green.copy(alpha = 0.2f)
                                    } else {
                                        Color.Red.copy(alpha = 0.2f)
                                    }
                                ),
                        )
                        Log.d("ANSWER-VALUE", "The answer value is ${answerIndexChoiceState.value}")
                        // The user alternative choices: answerAlternativeChoicesText
                        val annotatedString = buildAnnotatedString {
                            withStyle(
                                style = SpanStyle(
                                    fontWeight = FontWeight.Light,
                                    fontSize = 17.sp,
                                    color = if (correctAnswerState.value == true && index == answerIndexChoiceState.value) {
                                        Color.Green
                                    } else if (correctAnswerState.value == false && index == answerIndexChoiceState.value) {
                                        Color.Red
                                    } else {
                                        AppColors.mOffWhite
                                    }
                                )
                            ) {
                                append(answerAlternativeChoicesText)
                                //Text(text = answerAlternativeChoicesText)
                            }
                        }
                        Text(text = annotatedString)
                    }
                }
                // The next button
                NextButton(
                    onNextClicked = onNextClicked,
                    questionIndex = questionIndex
                )
                // TODO make a back button to
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

@Composable
fun ShowProgress(
    progressFactor: Float,
    score: Int = 12
) {
    val gradient = Brush.linearGradient(
        listOf(
            AppColors.progressBarColor,
            AppColors.progressBarColor2
        )
    )
    Row(
        modifier = Modifier
            .padding(3.dp)
            .fillMaxWidth()
            .height(45.dp)
            .background(Color.Transparent)
            .clip(
                RoundedCornerShape(
                    topStartPercent = 50,
                    topEndPercent = 50,
                    bottomStartPercent = 50,
                    bottomEndPercent = 50
                )
            )
            .border(
                width = 4.dp,
                shape = RoundedCornerShape(34.dp),
                brush = Brush
                    .linearGradient(
                        colors = listOf(AppColors.mLightPurple, AppColors.mLightPurple)
                    )
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // The button is just the progressbar and not an actually button
        Button(
            modifier = Modifier
                .fillMaxWidth(progressFactor)
                .background(brush = gradient),
            contentPadding = PaddingValues(1.dp),
            onClick = {},
            enabled = false,
            elevation = null,
            colors = buttonColors(
                contentColor = Color.Transparent,
                disabledContentColor = Color.Transparent
            )
        ) {
            Text(
                modifier = Modifier
                    .clip(shape = RoundedCornerShape(23.dp))
                    .fillMaxHeight(0.87f)
                    .fillMaxWidth()
                    .padding(6.dp),
                color = AppColors.mOffWhite,
                textAlign = TextAlign.Center,
                text = (score * 10).toString()
            )
        }
    }
}

@Composable
private fun NextButton(
    onNextClicked: (Int) -> Unit,
    questionIndex: MutableState<Int>
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Button(
            modifier = Modifier
                .padding(3.dp)
                .align(alignment = Alignment.Center),
            //.align(alignment = Alignment.CenterHorizontally),
            shape = RoundedCornerShape(34.dp),
            colors = buttonColors(contentColor = AppColors.mLightGray),
            onClick = { onNextClicked(questionIndex.value) }
        ) {
            Text(
                modifier = Modifier
                    .padding(4.dp),
                color = AppColors.mOffWhite,
                fontSize = 17.sp,
                text = "NEXT"
            )
        }
    }
}
