package com.beko.coex.ui.Register.JoinRoom

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels

import androidx.navigation.fragment.findNavController
import com.beko.coex.R
import com.beko.coex.databinding.FragmentJoinRoomBinding
import com.beko.coex.ui.Register.createroom.CreateRoomViewModel
import com.beko.coex.ui.main.HomePageActivity
import com.beko.coex.ui.register.JoinRoom.JoinRoomViewModel
import com.beko.coex.utils.Functions
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class JoinRoomFragment : Fragment() {
    private val joinRoomViewModel :JoinRoomViewModel by viewModels()
    private val createRoomViewModel : CreateRoomViewModel by viewModels()
    private lateinit var binding : FragmentJoinRoomBinding
    private lateinit var roomName : String
    private lateinit var roomPassword : String
        override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        binding =  FragmentJoinRoomBinding.inflate(layoutInflater)
        setOnClicks()
        setupObserver()
        return binding.root
    }

    private fun setupObserver() {
        joinRoomViewModel.isDone.observe(viewLifecycleOwner){
            if(it){
                joinRoomViewModel.room.observe(viewLifecycleOwner){ room ->
                    room.userUidList.add(Functions.getCurrentUserUid().toString())
                    createRoomViewModel.createRoom(room)
                }
            }
        }
        createRoomViewModel.isDone.observe(viewLifecycleOwner){
            if(it){
                startIntent()
            }
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
            findNavController().navigate(R.id.action_joinRoomFragment_to_createRoomFragment)
        }
        binding.joinRoomBtn.setOnClickListener {
            checkRoom()
        }
    }

    private fun checkRoom() {
        getTextInput()
        joinRoomViewModel.loginRoom(roomName,roomPassword)

    }

    private fun getTextInput() {
        roomName = binding.roomName.text.toString()
        roomPassword = binding.password.text.toString()
    }
}