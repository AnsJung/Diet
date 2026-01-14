package com.example.diet.ui.login

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Blue
import androidx.compose.ui.unit.dp

enum class LoginButtonType {
    LOGIN,
    SIGNUP
}

@Composable
fun LoginButton(
    type: LoginButtonType,
    enabled: Boolean = true,
    onButtonClicked: () -> Unit,
) {
    Button(
        onClick = onButtonClicked,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 8.dp, end = 8.dp, top = 10.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = type.let {
                when (it) {
                    LoginButtonType.LOGIN -> Blue
                    LoginButtonType.SIGNUP -> Color.DarkGray
                }
            },
        ),
        enabled = enabled
    ) {
        Text(
            text = type.let {
                when (it) {
                    LoginButtonType.LOGIN -> "Login"
                    LoginButtonType.SIGNUP -> "Sign Up"
                }
            },
            color = Color.White
        )
    }
}