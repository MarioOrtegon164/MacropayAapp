package com.macropay.prueba.ui.view.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import androidx.lifecycle.ViewModelProvider
import cn.pedant.SweetAlert.SweetAlertDialog
import com.macropay.prueba.databinding.ActivitySignUpBinding
import com.macropay.prueba.ui.viewmodel.fragments.AuthViewModel
import com.macropay.prueba.ui.viewmodel.fragments.SignUpViewModel
import com.macropay.prueba.utils.Utils

class SignUpActivity : AppCompatActivity() {

    private lateinit var viewModel: SignUpViewModel
    private lateinit var signUpBinding: ActivitySignUpBinding
    private lateinit var authViewModel: AuthViewModel
    private lateinit var email:String
    private lateinit var password:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        signUpBinding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(signUpBinding.root)

        viewModel = ViewModelProvider(this)[SignUpViewModel::class.java]
        authViewModel = ViewModelProvider(this)[AuthViewModel::class.java]

        setupEvents()
        setupObservers()
    }

    private fun setupObservers() {

        authViewModel.completeSignUp.observe(this){ shouldLogin ->
            if (shouldLogin){
                createUser()
            }
        }

        authViewModel.signUpResult.observe(this) { success ->
            if (success) {
                // Navegar a la pantalla de inicio
                showFinishMessage()
            } else {
                // Mostrar mensaje de error
                Utils().showErrorDialog(this,"El usuario y/o contraseña no son correctos.")
            }
        }
    }

    private fun showFinishMessage() {
        SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE)
            .setTitleText("Usuario creado")
            .setContentText("Usuario creado correctamente")
            .setConfirmText("Aceptar").setContentTextSize(10)
            .setConfirmClickListener {
                navigateToMainActivity()
                it.dismissWithAnimation()
            }.show()
    }

    private fun setupEvents() {
        signUpBinding.signupBtnRegister.setOnClickListener {
            authViewModel.doSignUp()
        }
    }

    private fun createUser() {
        email = signUpBinding.signupEtEmail.text.toString().trim()
        password = signUpBinding.signupEtPassword.text.toString().trim()

        //Validación de formulario
        if (!isEmptyEditText(
                signUpBinding.signupEtEmail, signUpBinding.signupEtPassword)) {
            authViewModel.signUp(email,password)
        }
        authViewModel.onSignUpComplete()
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

    private fun navigateToMainActivity() {
        this.finish()
    }
}