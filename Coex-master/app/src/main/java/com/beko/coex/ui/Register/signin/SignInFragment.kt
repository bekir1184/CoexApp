package com.beko.coex.ui.Register.signin

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.beko.coex.R
import com.beko.coex.databinding.FragmentSignInBinding
import com.beko.coex.ui.main.HomePageActivity
import com.beko.coex.utils.Constants.isValidMail
import com.beko.coex.utils.Constants.makeVisible
import com.beko.coex.utils.ErrorDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignInFragment : Fragment(R.layout.fragment_sign_in) {
    private lateinit var binding : FragmentSignInBinding
    private val signinViewModel : SigninViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        binding  = FragmentSignInBinding.inflate(layoutInflater)
        setOnClicks()
        setupObserver()
        return binding.root
    }


    private fun setupObserver() {
        signinViewModel.isDone.observe(this.viewLifecycleOwner , Observer {isLoggedIn ->
            if(isLoggedIn){
                val action = SignInFragmentDirections.actionSignInFragmentToCreateRoomFragment("","","")
                findNavController().navigate(action)
            }

        })
    }


    private fun setOnClicks() {
        binding.clickHereButton.setOnClickListener {
        findNavController().navigate(R.id.action_signInFragment_to_createAccountFragment)

        }
        binding.loginBtn.setOnClickListener {
            checkUser()
        }

    }
    private fun checkUser() {
        val mail = binding.loginmail.text.toString()
        val password = binding.loginpassword.text.toString()

        if (mail.isEmpty() || password.isEmpty()) {
            val error =  ErrorDialog("Hata","Tüm alanları doldurunuz")
            error.show(requireActivity().supportFragmentManager,"TAG")
        } else {
            if (!mail.isValidMail()) {
                val error =  ErrorDialog("Hata","Doğru mail adresini girdiğinizden emin olun ")
                error.show(requireActivity().supportFragmentManager,"TAG")
            }
            else if (password.length < 6) {
                val error =  ErrorDialog("Hata","Şifre 6 haneden kısa olmamalı. ")
                error.show(requireActivity().supportFragmentManager,"TAG")
            }
            else {
                binding.loginProgressBar.makeVisible()
                signinViewModel.loginAccount(mail, password)
            }
        }
    }
}