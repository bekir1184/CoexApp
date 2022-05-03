package com.beko.coex.ui.register.createaccount

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.beko.coex.R
import com.beko.coex.databinding.FragmentCreateAccountBinding
import com.beko.coex.models.User
import com.beko.coex.utils.Constants.isValidMail
import com.beko.coex.utils.Constants.makeInvisible
import com.beko.coex.utils.Constants.makeVisible
import com.beko.coex.utils.ErrorDialog
import com.beko.coex.utils.Functions
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreateAccountFragment : Fragment(R.layout.fragment_create_account) {
    private lateinit var binding : FragmentCreateAccountBinding
    private val createAccountViewModel : CreateAccountViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCreateAccountBinding.inflate(layoutInflater)
        setOnClicks()
        setupObserver()
        return binding.root
    }

    private fun setupObserver() {
        createAccountViewModel.isDone.observe(
            this.viewLifecycleOwner
        ) { isRegistered: Boolean ->
            binding.progressBarSignUp.makeInvisible()
            if (isRegistered) {
                val action = CreateAccountFragmentDirections.actionCreateAccountFragmentToCreateRoomFragment(
                        getUserData())
                findNavController().navigate(action)
            }else{
                val error =  ErrorDialog("Hata","Bir hata oluştu lütfen tekrar deneyiniz.")
                error.show(requireActivity().supportFragmentManager,"TAG")
            }
        }

    }



    private fun setOnClicks() {
        binding.signUpButton.setOnClickListener {
            createAccount()
        }
        binding.clickHereButton.setOnClickListener {
            findNavController().navigate(R.id.action_createAccountFragment_to_signInFragment)
        }

    }

    private fun createAccount() {
        val password = binding.passwordET.text.toString()
        if(getUserData().isUserValid(password)){
            binding.progressBarSignUp.makeVisible()
            createAccountViewModel.createUser(getUserData(),password)
        }else{
            val error =  ErrorDialog("Hata","Bir hata oluştu.")
            error.show(requireActivity().supportFragmentManager,"TAG")
        }
    }
    private fun User.isUserValid(password: String): Boolean {
        return if (this.email.isEmpty() || password.isEmpty() || this.name.isEmpty()) {
            val error =  ErrorDialog("Hata","Lütfen tüm alanları doldurunuz. ")
            error.show(requireActivity().supportFragmentManager,"TAG")
            false
        } else {
            if (!this.email.isValidMail()) {
                val error =  ErrorDialog("Hata","Geçersiz mail adresi , lütfen tekrar giriniz. ")
                error.show(requireActivity().supportFragmentManager,"TAG")
                false
            } else if (password.length < 6) {
                val error =  ErrorDialog("Hata","Şifre 6 hanaden uzun olmalı . ")
                error.show(requireActivity().supportFragmentManager,"TAG")
                false
            } else {
                true
            }
        }
    }

    private fun getUserData(): User {
        val name = binding.usernameET.text.toString()
        val email = binding.mailET.text.toString()
        return User(
            name,
            email,
        )

    }

}