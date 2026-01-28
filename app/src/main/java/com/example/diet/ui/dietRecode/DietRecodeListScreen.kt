package com.example.diet.ui.dietRecode

import android.R.attr.fontWeight
import android.R.attr.text
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.diet.model.Exercise
import com.example.diet.ui.components.AnimatedText

@Composable
fun DietRecodeListScreen(onAddClicked: () -> Unit) {
    val targetCalories = 1500
    val consumeCalories = 300
    val exerciseList = listOf(
        Exercise("러닝", 30, 200),
        Exercise("사이클", 45, 300)
    )
    Box(
        Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(16.dp)
    )
    {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {

            Spacer(Modifier.statusBarsPadding())
            AnimatedText(
                text = "DietRecords",
                color = Color.Blue,
                fontSize = 24.sp
            )
            Spacer(Modifier.height(20.dp))
            Text(
                "$consumeCalories / $targetCalories kcal",
                color = Color.White,
                fontSize = 20.sp,
            )
            Spacer(Modifier.height(20.dp))
            Box(contentAlignment = Alignment.Center) {
                AnimatedCircularProgressBar(
                    consumeValues = 300,
                    targetValues = 1500,
                    strokeWidth = 20.dp,
                    modifier = Modifier.size(200.dp)
                )
            }
            Spacer(Modifier.height(20.dp))
            LazyColumn() {
                items(exerciseList) { item ->
                    ExerciseRow(item)

                }
            }

        }

    }
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.BottomEnd
    ) {

        Box(
            modifier = Modifier
                .padding(bottom = 20.dp, end = 20.dp)
                .navigationBarsPadding()
                .size(50.dp)
                .clip(RoundedCornerShape(50))
                .background(Color.White)
                .clickable {
                    onAddClicked()
                },
            contentAlignment = Alignment.Center
        ) {

            Text(
                text = "+",
                fontSize = 30.sp,
                color = Color.Black
            )

        }

    }
}

@Composable
fun ExerciseRow(item: Exercise) {
    Row(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth()
            .background(
                Color(0xFF500371), shape = RoundedCornerShape(10.dp)
            )
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column() {
            Text(
                "${item.name}",
                color = Color(0xff00bfae),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                "${item.duration} 분",
                color = Color(0xff007dbf)
            )
        }
        Text(
            "${item.calorie} kcal",
            color = Color(0xff007a8c),
            fontSize = 16.sp,
        )
    }
}

@Composable
fun AnimatedCircularProgressBar(
    consumeValues: Int,
    targetValues: Int,
    strokeWidth: Dp,
    modifier: Modifier
) {
    val percentage = remember { Animatable(0f) }
    LaunchedEffect(Unit) {
        percentage.animateTo(
            targetValue = consumeValues / targetValues.toFloat(),
            animationSpec = tween(
                durationMillis = 1000
            )
        )
    }
    CircularProgressBar(
        percentage = percentage.value,
        strokeWidth = strokeWidth,
        modifier = modifier
    )
    val percentInt = (percentage.value * 100f).toInt()
    Text(
        text = "${percentInt}%",
        color = Color.White,
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold
    )
}

@Composable
fun CircularProgressBar(
    percentage: Float,
    strokeWidth: Dp,
    modifier: Modifier,
    color: Color = Color.Blue,
    background: Color = Color.LightGray
) {
    Canvas(modifier) {
        val size = size.minDimension
        val radius = size / 2
        val center = Offset(size / 2, size / 2)
        val startAngle = -90f
        val strokeWidth = strokeWidth.toPx()
        drawCircle(
            color = background,
            radius = radius - strokeWidth / 2,
            center = center,
            style = Stroke(width = strokeWidth)
        )

        drawArc(
            color = color,
            startAngle = startAngle,
            sweepAngle = percentage * 360,
            useCenter = false,
            topLeft = Offset(strokeWidth / 2, strokeWidth / 2),
            size = Size(size - strokeWidth, size - strokeWidth),
            style = Stroke(width = strokeWidth),
        )
    }
}

@Preview
@Composable
private fun PreviewDietRecodeListScreen() {
    DietRecodeListScreen(
        onAddClicked = {  }
    )
}