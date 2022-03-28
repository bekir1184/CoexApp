package com.beko.coex.ui.Register.createroom

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.beko.coex.R
import com.beko.coex.databinding.FragmentCreateRoomBinding

class CreateRoomFragment : Fragment() {

    private lateinit var binding : FragmentCreateRoomBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding  = FragmentCreateRoomBinding.inflate(layoutInflater)
        setOnClicks()
        return binding.root
    }

    private fun setOnClicks() {
        binding.clickHereButton.setOnClickListener {
            findNavController().navigate(R.id.action_createRoomFragment_to_joinRoomFragment)
        }
    }
}