package com.macropay.prueba.ui.viewmodel.fragments

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth

class AuthViewModel : ViewModel() {

    private val auth = FirebaseAuth.getInstance()

    private val _signInResult = MutableLiveData<Boolean>()
    private val _signUpResult = MutableLiveData<Boolean>()
    private val _completeSignIn = MutableLiveData<Boolean>()
    private val _completeSignUp = MutableLiveData<Boolean>()

    val signInResult: LiveData<Boolean>
        get() = _signInResult

    val completeSignIn: LiveData<Boolean>
        get() = _completeSignIn

    val completeSignUp: LiveData<Boolean>
        get() = _completeSignUp

    val signUpResult: LiveData<Boolean>
        get() = _signUpResult

    fun signIn(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                _signInResult.value = task.isSuccessful
            }
    }

    fun signUp(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                _signUpResult.value = task.isSuccessful
            }
    }

    fun onAuthComplete(): Boolean {
        return false
    }

    fun doSign() {
        _completeSignIn.value = true
    }
    fun onSignComplete() {
        _completeSignIn.value = false
    }

    fun onCompleteValidation() {
        TODO("Not yet implemented")
    }
}
