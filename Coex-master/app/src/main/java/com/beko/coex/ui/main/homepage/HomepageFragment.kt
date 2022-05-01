package com.beko.coex.ui.main.homepage

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.beko.coex.R
import com.beko.coex.databinding.FragmentHomepageBinding
import com.beko.coex.models.ExpenseStatusModel
import com.beko.coex.models.Room
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

    private fun setPieChart(myExpense : Int,otherPeopleExpense : Int) {
        val list: MutableList<PieEntry> = ArrayList()
        list.add(PieEntry(myExpense.toFloat(), "Kendi Harcamam"))
        list.add(PieEntry(otherPeopleExpense.toFloat(), "Diğer Kişlerin Harcamaları"))
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
    private fun getRoom(user : User) {
        homepageViewModel.roomInfo.value.also {
            homepageViewModel.getRoomInfo(user.room).also { setObserverRoom(user) }

        }
        homepageViewModel.expenseUserList.value.also {
            homepageViewModel.getExpenseUserList(user.room).also { setObserverRoom(user) }
        }
    }

    private fun setObserverRoom(user: User) {
        homepageViewModel.roomInfo.observe(viewLifecycleOwner, Observer { room ->
            homepageViewModel.expenseUserList.observe(viewLifecycleOwner, Observer { userList ->
                setData(user,room,userList)
            })

        })

    }

    private fun setObserver() {
        homepageViewModel.userInfo.observe(this.viewLifecycleOwner, Observer { user ->
            getRoom(user)
        })


    }



    private fun setData(user: User?, room: Room, userList: MutableList<User>) {
        val expensePerPerson = room.totalExpense / room.userUidList.size
        val otherPeopleExpense = room.totalExpense- user!!.totalCost
        binding.nameTV.text = user.email.split("@")[0]
        binding.roomName.text = user.room
        binding.expensePerPerson.text = "$expensePerPerson ₺"
        binding.myExpenseTotal.text = "${user.totalCost} ₺"
        binding.otherPeopleExpenseTotal.text ="$otherPeopleExpense ₺"
        binding.roomTotalExpense.text = "${room.totalExpense} ₺"
        setExpenseStatusRecyclerView(userList,expensePerPerson)
        setPieChart(user.totalCost,otherPeopleExpense)

    }

    @SuppressLint("WrongConstant")
    private fun setExpenseStatusRecyclerView(mutableList: MutableList<User>, expensePerPerson:Int) {
        val expenseStatusAdapter = ExpenseStatusAdapter()
        val mutableListExpense = mutableListOf<ExpenseStatusModel>()
        for(i in mutableList){
            println("$expensePerPerson - ${i.totalCost}")
            val cost =  i.totalCost - expensePerPerson
            mutableListExpense.add(ExpenseStatusModel(i.email.split("@")[0],cost))
        }
        expenseStatusAdapter.submitList(mutableListExpense)

        binding.expensStatusRecyler.layoutManager = LinearLayoutManager(context,
            LinearLayout.VERTICAL,false)
        binding.expensStatusRecyler.adapter = expenseStatusAdapter
    }

}