package com.example.diet.ui.main.setting

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.diet.R
import com.example.diet.ui.components.GradiantButton
import com.example.diet.ui.theme.DietTheme


@Composable
fun BranchSettingScreen(settingViewmodel: SettingViewmodel = viewModel()) {
    val state by settingViewmodel.uiState.collectAsStateWithLifecycle()
    val name = state.name
    val age  = state.age
    val height = state.height
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        settingViewmodel.event.collect { event ->
            when (event) {
                is SettingsUiEvent.ShowToast -> {
                    Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
    when (state.mode) {
        ScreenType.SETTING_INFO -> SettingScreen(
            name, age, height,
            onEditClicked = { settingViewmodel.onAction(SettingsAction.EditClicked) }
        )

        ScreenType.SETTING_EDIT -> SettingEditScreen(
            state = state,
            onAction = settingViewmodel::onAction
        )
    }
}

@Composable
fun SettingScreen(
    name: String,
    age: String,
    height: String,
    onEditClicked: () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(16.dp),
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "프로필",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
            )
            Spacer(modifier = Modifier.height(20.dp))
            Image(
                painter = painterResource(id = R.drawable.person),
                "",
                modifier = Modifier
                    .size(100.dp)
                    .clip(RoundedCornerShape(50))
            )
            Spacer(modifier = Modifier.height(20.dp))
            ProfileInfo(label = "이름", value = name)
            ProfileInfo(label = "나이", value = "${age} 살")
            ProfileInfo(label = "키", value = "${height} cm")
        }
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.BottomCenter
        ) {
            GradiantButton(
                "수정하기",
                onClick = onEditClicked,
                gradiantColors = listOf(Color(0xFF6A1B9A), Color(0xFFAB47BC)),
                modifier = Modifier.padding(start = 20.dp, end = 20.dp, bottom = 50.dp)
            )
        }
    }
}

@Composable
fun ProfileInfo(label: String, value: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .background(
                color = Color.DarkGray.copy(alpha = 0.5f),
                shape = RoundedCornerShape(8.dp)
            )
            .padding(16.dp)
    ) {
        Text(
            text = label,
            fontSize = 18.sp,
            color = Color.White,
            modifier = Modifier.padding(bottom = 4.dp)
        )
        Text(
            text = value,
            fontSize = 18.sp,
            color = Color.White,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 4.dp)
        )
    }
}

@Preview
@Composable
private fun PreviewSettingScreen() {
    DietTheme {
        SettingScreen(
            name = "",
            age = "TODO()",
            height = "TODO()",
            onEditClicked = { }
        )
    }
}