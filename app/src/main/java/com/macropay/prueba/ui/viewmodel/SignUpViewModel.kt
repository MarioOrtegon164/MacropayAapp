package com.macropay.prueba.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth

class SignUpViewModel : ViewModel() {
    private val auth = FirebaseAuth.getInstance()
    private val TAG = "MainViewModel"
    fun signUp(email:String,password:String){
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "createUserWithEmail:success")
                    val user = auth.currentUser
                    // updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "createUserWithEmail:failure", it.exception)
                    //updateUI(null)
                }
            }
    }
}