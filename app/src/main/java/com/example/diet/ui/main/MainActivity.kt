package com.example.diet.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.example.diet.ui.main.diet.DietScreen
import com.example.diet.ui.main.home.HomeScreen
import com.example.diet.ui.main.setting.SettingScreen
import com.example.diet.ui.theme.DietTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DietTheme {
                var currentScreen by rememberSaveable { mutableStateOf(DietScreenType.HOME) }

                Scaffold(
                    containerColor = Color.Black,
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        MainBottomBar(
                            currentScreen = currentScreen,
                            onTabSelected = { currentScreen = it }
                        )
                    }
                ) { innerPadding ->
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    ) {
                        when (currentScreen) {
                            DietScreenType.HOME -> HomeScreen()
                            DietScreenType.DIET -> DietScreen()
                            DietScreenType.SETTING -> SettingScreen()
                        }
                    }

                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    DietTheme {
//        Greeting("Android")
    }
}