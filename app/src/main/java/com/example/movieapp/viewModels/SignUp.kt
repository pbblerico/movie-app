package com.example.movieapp.viewModels

import android.content.Context
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import com.example.movieapp.models.User
import com.google.firebase.database.FirebaseDatabase

object SignUp {
    private var name = ""
    private var surname = ""
    private var email = ""
    private var password1 = ""
    private var password2 = ""
    private val auth = com.google.firebase.auth.FirebaseAuth.getInstance()
    private val TAG = "SIGN_UP"

    fun signup(name: String, surname: String, email: String, password1: String, password2: String, cont: Context): Boolean {
        this.name = name
        this.surname = surname
        this.email = email
        this.password1 = password1
        this.password2 = password2

        if(validateData(cont)) {
            auth.createUserWithEmailAndPassword(email, password1).addOnSuccessListener {
                Toast.makeText(cont, "Success", Toast.LENGTH_SHORT).show()
                createUser(cont)
            }.addOnFailureListener {e ->
                Toast.makeText(cont, "Failure", Toast.LENGTH_SHORT).show()
                Log.e(TAG, "$e")
            }
        }
        return auth.currentUser != null
    }

    private fun createUser(cont: Context) {
        val timestamp = System.currentTimeMillis()
        val uid = auth.uid

        val newUser = User(name, surname, email, password1, timestamp, uid!!)

        val ref = FirebaseDatabase.getInstance().getReference("Users")
        ref.child(uid).setValue(newUser)
            .addOnSuccessListener {
                Toast.makeText(cont, "Success", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener{e->
                Toast.makeText(cont, "Failure $e", Toast.LENGTH_SHORT).show()
                Log.d("TAG", "$e")
            }
    }

    private fun validateData(cont: Context): Boolean {
        if(name.isEmpty() || surname.isEmpty() || email.isEmpty() || password1.isEmpty() || password2.isEmpty()) {
            Toast.makeText(cont, "please fill all fields", Toast.LENGTH_SHORT).show()
            return false
        }
        if(password1.length < 6) {
            Toast.makeText(cont, "too short password, 6 character required", Toast.LENGTH_SHORT).show()
            return false
        }
        if(password1 != password2) {
            Toast.makeText(cont, "password is not the same", Toast.LENGTH_SHORT).show()
            return false
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(cont, "bad email address", Toast.LENGTH_SHORT).show()
            return false
        }

        auth.fetchSignInMethodsForEmail(email).addOnCompleteListener {
            if(it.result.signInMethods!!.size != 0) {
                Toast.makeText(cont, " user with such email already exists", Toast.LENGTH_SHORT).show()
            }
        }

        return true
    }
}
