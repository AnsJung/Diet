package com.example.diet.ui.main.setting

import android.R.attr.name
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.diet.ui.components.GradiantButton
import com.example.diet.ui.theme.DietTheme
import com.google.android.play.integrity.internal.ah

@Composable
fun SettingEditScreen(
    state: SettingsUiState,
    onAction: (SettingsAction) -> Unit,
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
                "프로필 수정",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
            )
            ProfileEditField("이름", state.editName) {
                onAction(SettingsAction.NameChanged(it))
            }
            ProfileEditField("나이", state.editAge) {
                onAction(SettingsAction.AgeChanged(it))
            }
            ProfileEditField("키", state.editHeight) {
                onAction(SettingsAction.HeightChanged(it))
            }
            Spacer(modifier = Modifier.height(20.dp))
            GradiantButton(
                "취소",
                onClick = {  onAction(SettingsAction.CancelEdit)  },
                gradiantColors = listOf(Color.Gray, Color.DarkGray),
            )
            Spacer(modifier = Modifier.height(20.dp))
            GradiantButton(
                "저장",
                onClick = { onAction(SettingsAction.SaveClicked) },
                gradiantColors = listOf(Color(0xFF6A1B9A), Color(0xFFAB47BC)),
            )
        }
    }
}

@Composable
fun ProfileEditField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Text(
            text = label,
            fontSize = 18.sp,
            color = Color.Gray,
            modifier = Modifier.padding(bottom = 4.dp)
        )
        TextField(
            value = value,
            onValueChange = {
                onValueChange(it)
            },
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.DarkGray, shape = RoundedCornerShape(8.dp))
                .padding(16.dp),
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.White,
                unfocusedIndicatorColor = Color.White,
                cursorColor = Color.White,
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White,
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent
            )
        )

    }
}

@Preview
@Composable
private fun PreviewSettingScreen() {
    DietTheme {
        SettingEditScreen(
            state = SettingsUiState(),
            onAction = {}
        )
    }
}