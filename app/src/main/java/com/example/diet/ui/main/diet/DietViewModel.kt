package com.example.diet.ui.main.diet

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.diet.model.Exercise
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldPath.documentId
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class DietViewModel : ViewModel() {
    private val db = FirebaseFirestore.getInstance()
    private val _exercises = MutableStateFlow<List<Exercise>>(emptyList())
    val exercises: StateFlow<List<Exercise>> = _exercises.asStateFlow()

    //운동기록 만들기
    fun createExercise(exercise: Exercise) = viewModelScope.launch {
        val user = FirebaseAuth.getInstance().currentUser ?: return@launch
        db.collection("diet_records")
            .document(user.uid)
            .collection("exercises")
            .add(exercise)
            .await()
    }

    // 불러오기
    fun readExercises() = viewModelScope.launch {
        try {
            val user = FirebaseAuth.getInstance().currentUser
            if (user == null) {
                _exercises.value = emptyList()
                return@launch
            }

            val list = db.collection("diet_records")
                .document(user.uid)
                .collection("exercises")
                .get()
                .await()
                .documents
                .mapNotNull { document ->
                    document.toObject(Exercise::class.java)
                        ?.copy(docId = document.id)
                }

            _exercises.value = list
        } catch (e: Exception) {
            _exercises.value = emptyList()
        }
    }
}