package com.beko.coex.ui.register.joinroom

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer

import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.beko.coex.R
import com.beko.coex.databinding.FragmentJoinRoomBinding
import com.beko.coex.models.Room
import com.beko.coex.models.User
import com.beko.coex.ui.main.HomePageActivity
import com.beko.coex.ui.register.createroom.CreateRoomViewModel
import com.beko.coex.utils.ErrorDialog
import com.beko.coex.utils.Functions
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class JoinRoomFragment : Fragment() {
    private lateinit var binding : FragmentJoinRoomBinding
    private val joinRoomViewModel : JoinRoomViewModel by viewModels()
    private val args : JoinRoomFragmentArgs by navArgs()

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
        joinRoomViewModel.room.observe(viewLifecycleOwner, Observer {
            if(it){
                startIntent()
            }else{
                val error =  ErrorDialog("Hata","Bir hata oluştu lütfen tekrar deneyiniz.")
                error.show(requireActivity().supportFragmentManager,"TAG")
            }
        })
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
        joinRoomViewModel.getRoom(
            binding.roomName.text.toString(),
            binding.password.text.toString(),
            args.user)
    }

    private fun clearText() {
        binding.password.setText("")
        binding.roomName.setText("")
    }

}