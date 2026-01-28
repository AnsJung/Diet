package com.example.diet.ui.main.diet

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import com.example.diet.ui.dietRecode.DietRecodeInputScreen
import com.example.diet.ui.dietRecode.DietRecodeListScreen


enum class DietRecodeScreen{
    DietRecordInfo,
    DietRecordAdd

}
@Composable
fun DietScreen(){
   var currentScreen by rememberSaveable { mutableStateOf(DietRecodeScreen.DietRecordInfo) }

    when(currentScreen){
        DietRecodeScreen.DietRecordInfo->{
            DietRecodeListScreen(
                onAddClicked = {
                    currentScreen = DietRecodeScreen.DietRecordAdd
                }
            )
        }
        else -> {
            DietRecodeInputScreen{
                currentScreen = DietRecodeScreen.DietRecordInfo
            }
        }
    }
}