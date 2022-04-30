package com.beko.coex.ui.main.homepage

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.beko.coex.R
import com.beko.coex.databinding.FragmentHomepageBinding
import com.beko.coex.models.User
import com.beko.coex.ui.MainActivity
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.google.firebase.auth.FirebaseAuth
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
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setHomePage()
        setOnClicks()
    }

    private fun setPieChart() {
        val list: MutableList<PieEntry> = ArrayList()
        list.add(PieEntry(700f, "Kendi Harcamam"))
        list.add(PieEntry(300f, "Diğer Kişlerin Harcamaları"))
        val pieDataSet = PieDataSet(list, "")
        pieDataSet.isUsingSliceColorAsValueLineColor = false
        pieDataSet.setColors(Color.rgb(23, 185, 120),Color.rgb(196,196,196))
        val pieData = PieData(pieDataSet)
        pieData.setDrawValues(false)
        binding.allExpensePieChart.data = pieData
        binding.allExpensePieChart.description.isEnabled = false
        binding.allExpensePieChart.animateY(1000)
        binding.allExpensePieChart.setDrawEntryLabels(false)
        binding.allExpensePieChart.invalidate()
    }

    private fun setOnClicks() {
        binding.exitBtn.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            val intent = Intent(this.requireContext(), MainActivity::class.java)
            startActivity(intent)
            activity?.finish()
        }
        binding.allExpenseBtn.setOnClickListener {

        }
        binding.addExpenseBtn.setOnClickListener {

        }
    }

    private fun setHomePage() {
        homepageViewModel.userInfo.value.also {
            homepageViewModel.getUserInfo().also { setObserver() }
        }
    }

    private fun setObserver() {
        homepageViewModel.userInfo.observe(this.viewLifecycleOwner, Observer { user ->
            setData(user)
            setPieChart()
        })
    }

    private fun setData(user: User?) {
        binding.nameTV.text = user!!.email.split("@")[0]
        binding.roomName.text = user.room
    }

}