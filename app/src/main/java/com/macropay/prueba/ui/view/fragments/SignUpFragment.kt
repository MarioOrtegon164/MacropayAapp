package com.macropay.prueba.ui.view.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.macropay.prueba.R
import com.macropay.prueba.databinding.FragmentSignUpBinding
import com.macropay.prueba.ui.viewmodel.fragments.AuthViewModel
import com.macropay.prueba.ui.viewmodel.fragments.SignUpViewModel

class SignUpFragment : Fragment(R.layout.fragment_sign_up) {

    companion object {
        fun newInstance() = SignUpFragment()
    }

    private lateinit var viewModel: SignUpViewModel
    private lateinit var signUpBinding: FragmentSignUpBinding
    private lateinit var authViewModel: AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[SignUpViewModel::class.java]
        authViewModel = ViewModelProvider(this)[AuthViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        signUpBinding = FragmentSignUpBinding.inflate(inflater,container,false)

        setupEvents()
        setupObservers()

        return  signUpBinding.root
    }

    private fun setupObservers() {
        authViewModel.signUpResult.observe(viewLifecycleOwner) { success ->
            if (success) {
                // Navegar a la pantalla de inicio
                Toast.makeText(context, "Firebase create chido", Toast.LENGTH_LONG).show()
            } else {
                // Mostrar mensaje de error
                Toast.makeText(context, "Error en create de firebase", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun setupEvents() {
        signUpBinding.signupBtnRegister.setOnClickListener {
            val email = signUpBinding.signupEtEmail.text.toString().trim()
            val password = signUpBinding.signupEtPassword.text.toString().trim()
            authViewModel.signUp(email,password)
        }
    }
}