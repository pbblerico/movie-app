package com.example.movieapp.viewModels

import android.content.Context
import android.util.Log
import android.widget.Toast

object LogIn {
    private var email = ""
    private var password = ""
    private val auth = com.google.firebase.auth.FirebaseAuth.getInstance()
    private val TAG = "LOG_IN"

    fun login(email: String, password: String, cont: Context): Boolean {
        this.email = email
        this.password = password

        if(validateData(cont)) {
            auth.signInWithEmailAndPassword(email, password).addOnSuccessListener {
                Toast.makeText(cont, "Success", Toast.LENGTH_SHORT).show()
            }.addOnFailureListener {e ->
                Toast.makeText(cont, "Wrong email or password", Toast.LENGTH_SHORT).show()
                Log.e("TAG", "$e")
            }
        }
        return LogIn.auth.currentUser != null
    }


    private fun validateData(cont: Context): Boolean {
        if(email.isEmpty() || password.isEmpty()) {
            Toast.makeText(cont, "please fill all fields", Toast.LENGTH_SHORT).show()
            return false
        }

        return true
    }
}