package com.example.zadanie.ui.fragments

import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.example.zadanie.R
import com.example.zadanie.databinding.FragmentSignUpBinding
import com.example.zadanie.helpers.Injection
import com.example.zadanie.helpers.PreferenceData
import com.example.zadanie.ui.viewmodels.AuthViewModel
import android.text.InputType.TYPE_CLASS_TEXT
import android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD
import com.example.zadanie.R.drawable.ic_baseline_visibility_24
import com.example.zadanie.R.drawable.ic_baseline_visibility_off_24
import com.example.zadanie.helpers.Config.PASSWORD_PATTERN

class SignUpFragment : Fragment() {
    private lateinit var binding: FragmentSignUpBinding
    private lateinit var authViewModel: AuthViewModel
    private var passwordVisible = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        authViewModel = ViewModelProvider(
            this,
            Injection.provideViewModelFactory(requireContext())
        ).get(AuthViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSignUpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val x = PreferenceData.getInstance().getUserItem(requireContext())
        if ((x?.uid ?: "").isNotBlank()) {
            Navigation.findNavController(view).navigate(R.id.action_to_bars)
            return
        }

        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            model = authViewModel
            signUpFragment = this@SignUpFragment
        }

        binding.signup.setOnClickListener {
            if (!validatePassword()){
                authViewModel.show("Password must contain 4 length, one number, one lower case letter, one upper case latter")
            }  else if (binding.username.text.toString().isBlank() || binding.password.text.toString().isBlank()){
                authViewModel.show("Fill in name and password")
            }  else if (binding.username.text.toString().isNotBlank() && binding.password.text.toString().isNotBlank()
                && binding.password.text.toString().compareTo(binding.repeatPassword.text.toString())==0) {
                authViewModel.signup(
                    binding.username.text.toString(),
                    binding.password.text.toString()
                )
            } else {
                authViewModel.show("Passwords must be same")
            }
        }

        binding.backToLoginButton.setOnClickListener {
            it.findNavController().navigate(R.id.action_to_login)
        }

        authViewModel.user.observe(viewLifecycleOwner){
            it?.let {
                PreferenceData.getInstance().putUserItem(requireContext(),it)
                Navigation.findNavController(requireView()).navigate(R.id.action_to_bars)
            }
        }

    }

    fun singUpValid(): Boolean {
        val usernameValid = validateUsername()
        val passwordValid = validatePassword()

        return usernameValid && passwordValid
    }

    fun validateUsername(): Boolean {

        return true
    }

    fun validatePassword(): Boolean {
        binding.password.let {
            if(!PASSWORD_PATTERN.toRegex().matches(it.text)){
                return false
            }
        }
        return true
    }

    fun passwordVisibilityToggle() {
        binding.password.apply {
            inputType = when (passwordVisible){
                true -> TYPE_CLASS_TEXT or TYPE_TEXT_VARIATION_PASSWORD
                false -> TYPE_CLASS_TEXT
            }
            setSelection(text.length)
        }

        binding.repeatPassword.apply {
            inputType = when (passwordVisible){
                true -> TYPE_CLASS_TEXT or TYPE_TEXT_VARIATION_PASSWORD
                false -> TYPE_CLASS_TEXT
            }
            setSelection(text.length)
        }

        binding.passwordVisibilityButton.apply {
            val imageSource = when (passwordVisible) {
                true -> ic_baseline_visibility_24
                false -> ic_baseline_visibility_off_24
            }
            setImageResource(imageSource)
        }
        passwordVisible = !passwordVisible
    }

}