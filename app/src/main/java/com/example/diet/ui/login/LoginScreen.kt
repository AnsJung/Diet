package com.example.diet.ui.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.diet.R
import com.example.diet.ui.theme.DietTheme

@Composable
fun LoginScreen(
    state: LoginUiState,
    onAction: (LoginAction) -> Unit,
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Black)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "logo",
            modifier = Modifier
                .padding(top = 100.dp, bottom = 32.dp)
                .size(200.dp)
                .clip(RoundedCornerShape(100.dp))
        )
        LoginTextField(
            title = "email",
            content = state.email
        ) {
            onAction(LoginAction.EmailChange(it))
        }
        LoginTextField(
            title = "password",
            content = state.pwd,
            isPassword = true
        ) {
            onAction(LoginAction.PwdChange(it))
        }
        state.errorMessage?.let {
            Text(
                text = it,
                color = Color.Red,
                fontSize = 16.sp,
                modifier = Modifier.padding(top = 10.dp)
            )
        }
        LoginButton(
            type = LoginButtonType.LOGIN,
            enabled = !state.isLoading
        ) {
            onAction(LoginAction.SignIn)
        }

        LoginButton(
            type = LoginButtonType.SIGNUP,
            enabled = !state.isLoading
        ) {
            onAction(LoginAction.SignUp)
        }
    }
    if (state.isLoading) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.6f)),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(color = Color.Blue)
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun LoginScreenPreview_Default() {
    DietTheme {
        LoginScreen(
            state = LoginUiState(
                email = "test@test.com",
                pwd = "123456",
                errorMessage = "This is an error message.",
            ),
            onAction = {}
        )
    }
}