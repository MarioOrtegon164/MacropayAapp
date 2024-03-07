package com.macropay.prueba.ui.viewmodel.fragments

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth

class MainViewModel : ViewModel() {

    private val auth = FirebaseAuth.getInstance()
    private val TAG = "MainViewModel"

    // Lógica de navegación utilizando LiveData
    private val _navigateToSecondFragment = MutableLiveData<Boolean>()
    val navigateToSecondFragment: LiveData<Boolean>
        get() = _navigateToSecondFragment

    fun onNavigateToSecondFragment() {
        _navigateToSecondFragment.value = true
    }

    fun onNavigationComplete() {
        _navigateToSecondFragment.value = false
    }

    //----

    fun login(email:String,password:String){
        auth.signInWithEmailAndPassword(email,password).addOnCompleteListener {
            if (it.isSuccessful){
                // Inicio de sesión exitoso
                // Puedes realizar acciones adicionales aquí si es necesario

            }else{
                // Manejar el fallo en el inicio de sesión
            }
        }
    }

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