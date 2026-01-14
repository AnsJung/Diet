package com.example.diet.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.diet.BuildConfig
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class LoginViewmodel : ViewModel() {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()
    private var authListener: FirebaseAuth.AuthStateListener? = null

    init {
        authListener = FirebaseAuth.AuthStateListener { firebaseAuth ->
            _uiState.update { it.copy(user = firebaseAuth.currentUser) }
        }
        auth.addAuthStateListener(authListener!!)
    }

    fun onAction(action: LoginAction) {
        when (action) {
            is LoginAction.EmailChange -> onEmailChange(action.newEmail)
            is LoginAction.PwdChange -> onPwdChange(action.newPwd)
            LoginAction.SignUp -> signUp()
            LoginAction.SignIn -> signIn()
        }
    }

    override fun onCleared() {
        authListener?.let { auth.removeAuthStateListener(it) }
        super.onCleared()
    }

    fun onEmailChange(newEmail: String) {
        _uiState.update { it.copy(email = newEmail) }
    }

    fun onPwdChange(newPwd: String) {
        _uiState.update { it.copy(pwd = newPwd) }
    }

    // 회원가입
    fun signUp() = viewModelScope.launch {
        _uiState.update { it.copy(isLoading = true, errorMessage = null) }
        if(BuildConfig.IS_DEV_MODE) {
            delay(2000)
        }
        try {
            auth.createUserWithEmailAndPassword(
                _uiState.value.email,
                _uiState.value.pwd
            ).await()
        } catch (e: Exception) {
            _uiState.update { it.copy(errorMessage = e.message) }
        } finally {
            _uiState.update { it.copy(isLoading = false) }
        }
    }

    // 로그인
    fun signIn() = viewModelScope.launch {
        _uiState.update { it.copy(isLoading = true, errorMessage = null) }
        if(BuildConfig.IS_DEV_MODE) {
            delay(2000)
        }
        try {
            auth.signInWithEmailAndPassword(
                _uiState.value.email,
                _uiState.value.pwd
            )
                .await()
        } catch (e: Exception) {
            _uiState.update { it.copy(errorMessage = e.message) }
        } finally {
            _uiState.update { it.copy(isLoading = false) }
        }
    }
}

data class LoginUiState(
    val email: String = "",
    val pwd: String = "",
    val errorMessage: String? = null,
    val isLoading: Boolean = false,
    val user: FirebaseUser? = null
)

sealed interface LoginAction {
    data class EmailChange(val newEmail: String) : LoginAction
    data class PwdChange(val newPwd: String) : LoginAction
    data object SignUp : LoginAction
    data object SignIn : LoginAction
}