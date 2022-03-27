package com.beko.coex.ui.Register.createroom

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.beko.coex.R
import com.beko.coex.databinding.FragmentCreateRoomBinding

class CreateRoomFragment : Fragment(R.layout.fragment_create_room) {

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

    }
}