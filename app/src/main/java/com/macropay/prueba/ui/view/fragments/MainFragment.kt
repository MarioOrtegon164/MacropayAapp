package com.macropay.prueba.ui.view.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.macropay.prueba.R
import com.macropay.prueba.databinding.FragmentMainBinding
import com.macropay.prueba.ui.viewmodel.fragments.AuthViewModel
import com.macropay.prueba.ui.viewmodel.fragments.MainViewModel

class MainFragment : Fragment(R.layout.fragment_main) {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel
    private lateinit var mainFragmentBinding: FragmentMainBinding
    private lateinit var authViewModel: AuthViewModel
    private val TAG = "MainFragment"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        authViewModel = ViewModelProvider(this)[AuthViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mainFragmentBinding = FragmentMainBinding.inflate(inflater, container, false)

        setupEvents()
        setupObservers()

        return mainFragmentBinding.root
    }

    private fun setupObservers() {

        viewModel.navigateToSignUpFragment.observe(viewLifecycleOwner) { shouldNavigate ->
            if (shouldNavigate) {
                navigateToFragment(R.id.action_mainFragment_to_signUpFragment)
                // Resetear el estado de navegación en el ViewModel
                viewModel.onNavigationToSignUpComplete()
            }
        }

        viewModel.navigateToHomeFragment.observe(viewLifecycleOwner) { shouldNavigate ->
            if (shouldNavigate) {
                navigateToFragment(R.id.action_mainFragment_to_homeFragment)
                // Resetear el estado de navegación en el ViewModel
                viewModel.onNavigationToHomeComplete()
            }
        }

        authViewModel.completeSignIn.observe(viewLifecycleOwner){ shouldLogin ->
            if (shouldLogin){
                authViewModel.signInResult.observe(viewLifecycleOwner) { success ->
                    if (success) {
                        // Navegar a la pantalla de inicio
                        Toast.makeText(context, "Firebase login chido", Toast.LENGTH_LONG).show()
                        viewModel.onNavigateToHomeFragment()
                    } else {
                        // Mostrar mensaje de error
                        Toast.makeText(context, "Error en login de firebase", Toast.LENGTH_LONG).show()
                    }
                    authViewModel.onSignComplete()
                }
            }
           // authViewModel.onSignComplete()
        }
    }

    private fun setupEvents() {
        mainFragmentBinding.btnLogin.setOnClickListener {
            val email = mainFragmentBinding.editTextUsername.text.toString().trim()
            val password = mainFragmentBinding.editTextPassword.text.toString().trim()
            authViewModel.doSign()
            authViewModel.signIn(email,password)
        }

        val navigateButton = mainFragmentBinding.btnSignUp
        navigateButton.setOnClickListener {
            viewModel.onNavigateToSignUpFragment()
        }

    }

    private fun navigateToFragment(fragmentId:Int) {
        try {
            findNavController().navigate(fragmentId)

        }catch (e:Exception){
            Log.d(TAG, e.toString())
        }
    }

}