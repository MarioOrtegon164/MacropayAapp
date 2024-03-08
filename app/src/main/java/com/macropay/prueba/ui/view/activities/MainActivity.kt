package com.macropay.prueba.ui.view.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import androidx.lifecycle.ViewModelProvider
import com.macropay.prueba.databinding.ActivityMainBinding
import com.macropay.prueba.ui.viewmodel.AuthViewModel
import com.macropay.prueba.ui.viewmodel.MainViewModel
import com.macropay.prueba.utils.Utils

class MainActivity : AppCompatActivity() {

    private lateinit var mainActivityBinding:ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    //private lateinit var mainFragmentBinding: FragmentMainBinding
    private lateinit var authViewModel: AuthViewModel
    private lateinit var email:String
    private lateinit var password:String
    private val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainActivityBinding = ActivityMainBinding.inflate(layoutInflater)


        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        authViewModel = ViewModelProvider(this)[AuthViewModel::class.java]

        setupEvents()
        setupObservers()

        setContentView(mainActivityBinding.root)

    }

    private fun setupObservers() {

        authViewModel.completeSignIn.observe(this){ shouldLogin ->
            if (shouldLogin){
                login()
            }
        }

        authViewModel.signInResult.observe(this) { success ->
            if (success) {
                // Navegar a la pantalla de inicio
                navigateToHomeActivity()
            } else {
                // Mostrar mensaje de error
                Utils().showErrorDialog(this,"El usuario y/o contraseña no son correctos.")
            }
        }
    }

    private fun setupEvents() {
        mainActivityBinding.btnLogin.setOnClickListener {
            authViewModel.doSignIn()
        }

        val navigateButton = mainActivityBinding.btnSignUp
        navigateButton.setOnClickListener {
            navigateToSignUpActivity()
        }
    }

    //Llamada al login con firebase
    private fun login() {
        email = mainActivityBinding.editTextUsername.text.toString().trim()
        password = mainActivityBinding.editTextPassword.text.toString().trim()

        //Validación de formulario
        if (!isEmptyEditText(
                mainActivityBinding.editTextUsername, mainActivityBinding.editTextPassword)) {
            authViewModel.signIn(email,password)
        }
        authViewModel.onSignInComplete()
    }

    private fun navigateToSignUpActivity() {

        val intent = Intent(this, SignUpActivity::class.java)
        startActivity(intent)
        //finish()

    }

    private fun navigateToHomeActivity() {
        val intent = Intent(this, HomeActivity::class.java)
        intent.putExtra("email", email)
        startActivity(intent)
        finish()
    }

    //Validacion de formulario
    private fun isEmptyEditText(vararg list: EditText): Boolean {
        for (edittext in list) {
            if (edittext.text.toString() == "") {
                edittext.error = "Este campo es requerido"
                edittext.requestFocus()
                return true
            }
        }
        return false
    }



}