package com.macropay.prueba.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth

class MainViewModel : ViewModel() {

    private val auth = FirebaseAuth.getInstance()
    private val TAG = "MainViewModel"

    // Lógica de navegación utilizando LiveData
    private val _navigateToSignUpFragment = MutableLiveData<Boolean>()
    val navigateToSignUpFragment: LiveData<Boolean>
        get() = _navigateToSignUpFragment

    private val _navigateToHomeFragment = MutableLiveData<Boolean>()
    val navigateToHomeFragment: LiveData<Boolean>
        get() = _navigateToHomeFragment

    // LiveData para manejar el estado de la validación
    val validationStateEmail = MutableLiveData<ValidationState>()
    val validationStatePassword = MutableLiveData<ValidationState>()

    fun onNavigateToSignUpFragment() {
        _navigateToSignUpFragment.value = true
    }

    fun onNavigationToSignUpComplete() {
        _navigateToSignUpFragment.value = false
    }

    fun onNavigateToHomeFragment() {
        _navigateToHomeFragment.value = true
    }

    fun onNavigationToHomeComplete() {
        _navigateToHomeFragment.value = false
    }

}

sealed class ValidationState {
    object Valid : ValidationState()
    data class Invalid(val error: String) : ValidationState()
}