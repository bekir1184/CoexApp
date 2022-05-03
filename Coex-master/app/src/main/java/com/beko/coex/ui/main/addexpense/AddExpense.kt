package com.beko.coex.ui.main.addexpense

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavArgs
import androidx.navigation.fragment.navArgs
import com.beko.coex.R
import com.beko.coex.databinding.FragmentAddExpenseBinding
import com.beko.coex.databinding.FragmentAllExpenseBinding
import com.beko.coex.models.Expense
import com.beko.coex.utils.ErrorDialog
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.cos

@AndroidEntryPoint
class AddExpense : Fragment() {
    private lateinit var binding : FragmentAddExpenseBinding
    private var cost = 0
    private var expenseName = ""
    private var expenseDecs = ""
    private var expenseCategory ="bill"
    private val addExpenseViewModel : AddExpenseViewModel by viewModels()
    private val args : AddExpenseArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddExpenseBinding.inflate(inflater,container,false)
        setOnClicks()
        checkInputs()
        setObserver()
        setToggleButtons()
        return binding.root
    }

    private fun setObserver() {
        addExpenseViewModel.isDone.observe(viewLifecycleOwner, Observer {
            if(it){
                binding.expenseCostTI.setText("")
                binding.expenseTitleTI.setText("")
                binding.expenseDescTI.setText("")
            }
        })
    }

    @SuppressLint("ResourceAsColor")
    private fun setOnClicks() {
        binding.addExpenseBtn.isEnabled = binding.expenseCostTI.text.toString().trim().isNotEmpty()
        binding.addExpenseBtn.setOnClickListener {
            getExpenseData()
            val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
            val currentDate = sdf.format(Date())
            val room = args.room
            val user = args.user
            room.totalExpense += cost
            user.totalCost += cost
            when(expenseCategory){
                "food" -> {
                    room.totalFood += cost
                    user.foodCost += cost
                }
                "other" -> {
                    room.totalOther += cost
                    user.otherCost += cost
                }
                "rent" -> {
                    room.totalRent += cost
                    user.houseCost += cost
                }
                "bill" -> {
                    room.totalBill += cost
                    user.billCost += cost
                }

            }
            addExpenseViewModel.addExpense(room, Expense(expenseName,expenseCategory,cost.toDouble(),currentDate,expenseDecs,user))
        }

    }


    private fun getExpenseData(){
        cost = binding.expenseCostTI.text.toString().toInt()
        expenseName = binding.expenseTitleTI.text.toString()
        expenseDecs = binding.expenseDescTI.text.toString()
    }
    private fun checkInputs() {

        binding.expenseCostTI.addTextChangedListener( object : TextWatcher{
            override fun beforeTextChanged(
                s: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {
                binding.addExpenseBtn.isEnabled = !s.toString().trim().isEmpty()
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.addExpenseBtn.isEnabled = !s.toString().trim().isEmpty()
            }
            override fun afterTextChanged(s: Editable?) {
                binding.addExpenseBtn.isEnabled = !s.toString().trim().isEmpty()
            }

        })

    }

    @SuppressLint("SetTextI18n")
    private fun setToggleButtons() {
        binding.toggleButtonGroup.addOnButtonCheckedListener { group, checkedId, isChecked ->
            when(checkedId){
                binding.billOp.id  -> {
                    expenseCategory = "bill"
                }
                binding.foodOp.id  -> {
                    expenseCategory = "food"
                }
                binding.rentOp.id  -> {
                    expenseCategory = "rent"
                }
                binding.otherOp.id -> {
                    expenseCategory = "other"
                }
            }
        }
    }
}