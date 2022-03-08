package com.beko.coex.ui.Register.signin

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.beko.coex.R
import com.beko.coex.ui.MainActivity
import com.beko.coex.ui.main.HomePageActivity
import com.beko.coex.utils.Constants.isValidMail
import com.beko.coex.utils.Constants.makeVisible
import com.beko.coex.utils.ErrorDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_sign_in.*

@AndroidEntryPoint
class SignInFragment : Fragment(R.layout.fragment_sign_in) {
    private val signinViewModel : SigninViewModel by viewModels()
    override fun onStart() {
        super.onStart()
        setOnClicks()
        setupObserver()
    }

    private fun setupObserver() {
        signinViewModel.isDone.observe(this.viewLifecycleOwner , Observer {isLoggedIn ->
            if(isLoggedIn)
                    startIntent(HomePageActivity::class.java)
        })
    }
    private fun startIntent(className: Class<*>) {
        startActivity(
            Intent(
                this.requireContext(),
                className
            ).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
        ).also { this.requireActivity().finish() }
    }


    private fun setOnClicks() {
        click_here_button.setOnClickListener {
            findNavController().navigate(R.id.action_signInFragment_to_createAccountFragment)
        }
        button.setOnClickListener {
            checkUser()
        }

    }
    private fun checkUser() {
        val mail = loginmail.text.toString()
        val password = loginpassword.text.toString()

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
                loginProgressBar.makeVisible()
                signinViewModel.loginAccount(mail, password)
            }
        }
    }
}