package com.beko.coex.ui.Register.JoinRoom

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.beko.coex.databinding.FragmentJoinRoomBinding


class JoinRoomFragment : Fragment() {
    private lateinit var binding : FragmentJoinRoomBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        binding =  FragmentJoinRoomBinding.inflate(layoutInflater)
        return binding.root
    }
}