package com.example.diet.ui.login

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.diet.BuildConfig
import com.example.diet.MainActivity
import com.example.diet.ui.theme.DietTheme


class LoginActivity : ComponentActivity() {
    private val viewModel: LoginViewmodel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DietTheme {
                val state by viewModel.uiState.collectAsStateWithLifecycle()
                if(BuildConfig.IS_DEV_MODE) {
                    viewModel.onAction(LoginAction.EmailChange("ljh1030@gmail.com"))
                    viewModel.onAction(LoginAction.PwdChange("ljh1030"))
                }
                if (state.user != null) {
                    LaunchedEffect(state.user) {
                        startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                        finish()
                    }
                }
                if (state.user == null) {
                    LoginScreen(
                        state = state,
                        onAction = viewModel::onAction
                    )
                }
            }
        }
    }
}
