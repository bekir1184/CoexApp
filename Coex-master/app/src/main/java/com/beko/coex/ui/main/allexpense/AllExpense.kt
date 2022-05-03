package com.beko.coex.ui.main.allexpense

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.beko.coex.R
import com.beko.coex.databinding.FragmentAllExpenseBinding
import com.beko.coex.models.Expense
import com.beko.coex.utils.ErrorDialog

class AllExpense : Fragment() {
    private lateinit var binding : FragmentAllExpenseBinding
    private val args : AllExpenseArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAllExpenseBinding.inflate(inflater,container,false)
        setExpenseAdapter()
        return binding.root
    }

    @SuppressLint("WrongConstant")
    private fun setExpenseAdapter() {
        val expenseAdapter = ExpenseAdapter()
        expenseAdapter.setOnItemCoinClickListener {
            val error =  ErrorDialog(it.expenseName,it.descripton)
            error.show(requireActivity().supportFragmentManager,"TAG")
        }
        val mutableListExpense = args.room.expenseList.reversed()
        expenseAdapter.submitList(mutableListExpense)
        binding.expenseRecyclerView.layoutManager = LinearLayoutManager(context,
            LinearLayout.VERTICAL,false)
        binding.expenseRecyclerView.adapter = expenseAdapter
    }

}