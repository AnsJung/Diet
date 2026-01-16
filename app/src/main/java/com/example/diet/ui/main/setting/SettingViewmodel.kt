package com.example.diet.ui.main.setting

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class SettingViewmodel : ViewModel() {

    private val db = FirebaseFirestore.getInstance()
    private val _uiState = MutableStateFlow(SettingsUiState())
    val uiState: StateFlow<SettingsUiState> = _uiState.asStateFlow()
    private val _event = MutableSharedFlow<SettingsUiEvent>()
    val event = _event.asSharedFlow()

    init {
        loadProfileData()
    }

    // 유저 정보 업데이트
    fun saveProfileData(
        name: String,
        age: String, height: String,
    ) = viewModelScope.launch {
        val profile = hashMapOf(
            "name" to name,
            "age" to age,
            "height" to height
        )
        val user = FirebaseAuth.getInstance().currentUser ?: return@launch
        try {
            db.collection("profiles")
                .document(user.uid)
                .set(profile)
                .await()

            _uiState.update {
                it.copy(
                    name = name,
                    age = age,
                    height = height,
                    mode = ScreenType.SETTING_INFO
                )
            }
            _event.emit(SettingsUiEvent.ShowToast("프로필이 저장되었습니다."))
        } catch (e: Exception) {
            Log.e(
                "JH",
                e.printStackTrace()
                    .toString()
            )
            _event.emit(SettingsUiEvent.ShowToast("프로필 저장에 실패했습니다."))
        }
    }

    // 유저 정보 불러오기
    fun loadProfileData() = viewModelScope.launch {
        val user = FirebaseAuth.getInstance().currentUser ?: return@launch
        try {
            val document = db.collection("profiles")
                .document(user.uid)
                .get()
                .await()

            if (document.exists()) {
                val name = document.getString("name") ?: ""
                val age = document.getString("age") ?: ""
                val height = document.getString("height") ?: ""

                _uiState.update {
                    it.copy(
                        name = name,
                        age = age,
                        height = height,
                    )
                }
            } else {
                _uiState.update { it.copy(name = "", age = "", height = "") }
            }
        } catch (e: Exception) {
            _uiState.update { it.copy(name = "", age = "", height = "") }
        }
    }

    fun onAction(action: SettingsAction) {
        when (action) {
            SettingsAction.EditClicked -> {
                _uiState.update {
                    it.copy(
                        mode = ScreenType.SETTING_EDIT,
                        editName = it.name,
                        editAge = it.age,
                        editHeight = it.height
                    )
                }
            }

            SettingsAction.CancelEdit -> {
                _uiState.update { it.copy(mode = ScreenType.SETTING_INFO) }
            }

            SettingsAction.SaveClicked -> {
                val s = _uiState.value
                saveProfileData(s.editName, s.editAge, s.editHeight)
            }

            is SettingsAction.NameChanged -> {
                _uiState.update { it.copy(editName = action.newName) }
            }

            is SettingsAction.AgeChanged -> {
                _uiState.update { it.copy(editAge = action.newAge) }
            }

            is SettingsAction.HeightChanged -> {
                _uiState.update { it.copy(editHeight = action.newHeight) }
            }
        }
    }
}

enum class ScreenType {
    SETTING_INFO,
    SETTING_EDIT
}

data class SettingsUiState(
    val name: String = "",
    val age: String = "",
    val height: String = "",
    val editName: String = "",
    val editAge: String = "",
    val editHeight: String = "",
    val mode: ScreenType = ScreenType.SETTING_INFO
)

sealed interface SettingsAction {
    data object EditClicked : SettingsAction
    data object CancelEdit : SettingsAction
    data object SaveClicked : SettingsAction

    data class NameChanged(val newName: String) : SettingsAction
    data class AgeChanged(val newAge: String) : SettingsAction
    data class HeightChanged(val newHeight: String) : SettingsAction
}

sealed interface SettingsUiEvent {
    data class ShowToast(val message: String) : SettingsUiEvent
}