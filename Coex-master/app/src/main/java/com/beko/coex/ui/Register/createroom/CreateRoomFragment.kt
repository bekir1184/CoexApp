package com.beko.coex.ui.Register.createroom

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavArgs
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.beko.coex.R
import com.beko.coex.databinding.FragmentCreateRoomBinding
import com.beko.coex.models.Room
import com.beko.coex.models.User
import com.beko.coex.ui.main.HomePageActivity
import com.beko.coex.utils.Constants.makeVisible
import com.beko.coex.utils.ErrorDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreateRoomFragment : Fragment() {
    private val args : CreateRoomFragmentArgs by navArgs()
    private val createRoomViewModel: CreateRoomViewModel by viewModels()
    private lateinit var binding : FragmentCreateRoomBinding
    private lateinit var user : User
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding  = FragmentCreateRoomBinding.inflate(layoutInflater)
        setOnClicks()
        setupObserver()
        user = User(args.name,args.mail,args.uid)
        return binding.root
    }

    private fun setupObserver() {
        createRoomViewModel.isDone.observe(this.viewLifecycleOwner) { isDone ->
            if (isDone) startIntent()

        }
    }
    private fun startIntent() {
        startActivity(
            Intent(
                this.requireContext(),
                HomePageActivity::class.java
            )
        ).also { this.requireActivity().finish() }
    }

    private fun setOnClicks() {
        binding.clickHereButton.setOnClickListener {
            findNavController().navigate(R.id.action_createRoomFragment_to_joinRoomFragment)
        }
        binding.createRoomBtn.setOnClickListener {
            checkRoomData()
        }
    }

    private fun checkRoomData() {
        val roomName = binding.roomName.text.toString()
        val password = binding.password.text.toString()
        val userList = mutableListOf<User>()
        userList.add(user)
        val room = Room(roomName,password, userList)

        if(roomName.isEmpty() || password.isEmpty()){
            val error =  ErrorDialog("Hata","Tüm alanları doldurunuz")
            error.show(requireActivity().supportFragmentManager,"TAG")
        }else{
            if (roomName.isEmpty()) {
                val error =  ErrorDialog("Hata","Bu oda adı kullanılıyor . Lütfen başka bir isim deneyiniz")
                error.show(requireActivity().supportFragmentManager,"TAG")
            }
            else if (password.length < 6) {
                val error =  ErrorDialog("Hata","Şifre 6 haneden kısa olmamalı. ")
                error.show(requireActivity().supportFragmentManager,"TAG")
            }
            else {
                binding.progressBarCreateRoom.makeVisible()
                createRoomViewModel.loginAccount(room)
            }
        }

    }

}