package com.example.communitywaterqualitymonitoring.data

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class AuthRepository {
    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()

    fun getCurrentUser() = auth.currentUser

    suspend fun signIn(email: String, pass: String) {
        auth.signInWithEmailAndPassword(email, pass).await()
    }

    suspend fun signUp(email: String, pass: String, name: String, phone: String) {
        val result = auth.createUserWithEmailAndPassword(email, pass).await()
        val user = User(
            userId = result.user?.uid ?: "",
            name = name,
            email = email,
            phone = phone,
            role = UserRole.USER
        )
        db.collection("users").document(user.userId).set(user).await()
    }

    fun signOut() = auth.signOut()
}
