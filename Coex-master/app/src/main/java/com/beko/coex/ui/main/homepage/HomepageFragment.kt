package com.beko.coex.ui.main.homepage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.viewmodel.compose.viewModel
import com.beko.coex.R
import com.beko.coex.databinding.FragmentHomepageBinding
import com.beko.coex.models.User
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomepageFragment : Fragment(R.layout.fragment_homepage) {
    private lateinit var binding :FragmentHomepageBinding
    private val homepageViewModel : HomepageViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        binding = FragmentHomepageBinding.inflate(layoutInflater)
        setObserver()
        return binding.root
    }
    private fun setObserver() {
        homepageViewModel.userInfo.observe(this.viewLifecycleOwner, Observer { user ->
            setData(user)
        })
    }

    private fun setData(user: User?) {
        binding.nameTV.text = user!!.name
    }

}