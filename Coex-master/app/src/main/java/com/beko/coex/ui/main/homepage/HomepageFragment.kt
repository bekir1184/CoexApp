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
import androidx.navigation.fragment.findNavController
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
    private lateinit var userGlobal: User
    private lateinit var roomGlobal : Room
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        binding = FragmentHomepageBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHomePage()
        setOnClicks()
        refreshHomepage()
        checkRoomApprovalComplete()
    }
    private fun refreshHomepage() {
        binding.swipeToRefresh.setOnRefreshListener {
            setHomePage()
        }
    }

    private fun setPieChart(myExpense : Int,otherPeopleExpense : Int) {
        val list: MutableList<PieEntry> = mutableListOf()
        list.add(PieEntry(myExpense.toFloat(), "Kendi Harcamam"))
        list.add(PieEntry(otherPeopleExpense.toFloat(), "Diğer Kişlerin Harcamaları"))
        val pieDataSet = PieDataSet(list, "")
        pieDataSet.isUsingSliceColorAsValueLineColor = false
        pieDataSet.setColors(Color.rgb(23, 185, 120),Color.rgb(196,196,196))
        val pieData = PieData(pieDataSet)
        pieData.setDrawValues(false)
        binding.allExpensePieChart.legend.isEnabled = false
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
    }
    private fun setDirectOnClicks(){
        binding.allExpenseBtn.setOnClickListener {
            val action = HomepageFragmentDirections.actionHomepageFragmentToAllExpense(roomGlobal,userGlobal)
            findNavController().navigate(action)
        }
        binding.addExpenseBtn.setOnClickListener {
            val action = HomepageFragmentDirections.actionHomepageFragmentToAddExpense(userGlobal,roomGlobal)
            findNavController().navigate(action)
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
    private fun checkRoomApprovalComplete(){

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
            binding.swipeToRefresh.isRefreshing =false
        })
    }



    @SuppressLint("SetTextI18n")
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
        userGlobal = user
        roomGlobal = room
        setDirectOnClicks()
        setConfirmButton(user,room,userList)
    }
    private fun setConfirmButton(user: User?,room: Room, userList: MutableList<User>) {
        binding.btnConfrimPayment.isEnabled = (room.approvalStarted == 1 && user!!.approvalStatus == 0) ||
                (room.approvalStarted == 0 && user!!.approvalStatus == 0)

        if(room.approvalStarted == 1 && user!!.approvalStatus== 1){
            binding.btnConfrimPayment.text = "Ödeme onayı bekleniyor"
        }else if(room.approvalStarted == 1 && user!!.approvalStatus== 0){
            binding.btnConfrimPayment.text = "Ödeme onayı başladı , onaylamak için tıklayınız"
        }else if(room.approvalStarted == 0 ){
            binding.btnConfrimPayment.text = "Ödeme onayı başlat"
        }
        var isAnyoneRefusing = false
        for (user in userList){
            if(user.approvalStatus==0){
                isAnyoneRefusing = true
                break
            }
        }
        binding.btnConfrimPayment.setOnClickListener {
            user!!.approvalStatus = 1
            room.approvalStarted = 1
            homepageViewModel.setRoom(room, user)
            setHomePage()
        }
        if(!isAnyoneRefusing){
            deleteAllRoomData(isAllUserApprove(userList),userList)
        }

    }

    private fun deleteAllRoomData(allUserApprove: Boolean,userList: MutableList<User>) {
        when(allUserApprove) {
             true -> {
                roomGlobal.approvalStarted = 0
                roomGlobal.expenseList = mutableListOf()
                roomGlobal.totalBill = 0
                roomGlobal.totalExpense = 0
                roomGlobal.totalFood = 0
                roomGlobal.totalRent = 0
                roomGlobal.totalOther = 0
                homepageViewModel.setRoom(roomGlobal,userGlobal)
                for (user in userList){
                    user.approvalStatus = 0
                    user.billCost = 0
                    user.foodCost = 0
                    user.houseCost = 0
                    user.otherCost = 0
                    user.totalCost = 0
                    homepageViewModel.setUser(user)
                    println(user)
                }
            }
            else -> {

            }
        }
    }

    private fun isAllUserApprove(userList: MutableList<User>) : Boolean{
        for(user in userList){
            if (user.approvalStatus == 0){
                return false
            }
        }
        return true
    }


    @SuppressLint("WrongConstant")
    private fun setExpenseStatusRecyclerView(mutableList: MutableList<User>, expensePerPerson:Int) {
        val expenseStatusAdapter = ExpenseStatusAdapter()
        val mutableListExpense = mutableListOf<ExpenseStatusModel>()
        for(i in mutableList){
            val cost =  i.totalCost - expensePerPerson
            mutableListExpense.add(ExpenseStatusModel(i.email.split("@")[0],cost))
        }
        expenseStatusAdapter.submitList(mutableListExpense)

        binding.expensStatusRecyler.layoutManager = LinearLayoutManager(context,
            LinearLayout.VERTICAL,false)
        binding.expensStatusRecyler.adapter = expenseStatusAdapter
    }

}