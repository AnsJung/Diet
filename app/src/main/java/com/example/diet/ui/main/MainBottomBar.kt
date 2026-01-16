package com.example.diet.ui.main

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.diet.R
import com.example.diet.ui.theme.DietTheme

enum class DietScreenType(val icon: Int) {
    HOME(R.drawable.outline_add_home_24),
    DIET(R.drawable.outline_alarm_24),
    SETTING(R.drawable.outline_settings_24)
}

@Composable
fun MainBottomBar(
    currentScreen: DietScreenType,
    onTabSelected: (DietScreenType) -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Black)
            .navigationBarsPadding()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.DarkGray)
                .height(80.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            DietScreenType.entries.forEach { screen ->
                val isSelected = screen == currentScreen
                val color by animateColorAsState(
                    targetValue = if (isSelected) Color.White else Color.Gray,
                    animationSpec = spring(),
                    label = ""
                )
                val scale by animateFloatAsState(
                    targetValue = if (isSelected) 1.2f else 1.0f,
                    animationSpec = spring(),
                    label = ""
                )
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .scale(scale)
                        .padding(3.dp)
                        .clickable { onTabSelected(screen) }
                ) {
                    Icon(
                        painter = painterResource(id = screen.icon),
                        contentDescription = screen.name,
                        tint = color
                    )
                    Text(screen.name, color = color)
                }
            }
        }
    }
}

@Preview
@Composable
private fun PreviewMainBottomBar() {
    DietTheme() {
        MainBottomBar(
            currentScreen = DietScreenType.HOME,
            onTabSelected = {}
        )
    }

}