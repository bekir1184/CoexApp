package com.beko.coex.ui.main.allexpense

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.beko.coex.R
import com.beko.coex.databinding.ExpenseItemBinding
import com.beko.coex.databinding.OneRowExpenseStatusBinding
import com.beko.coex.models.Expense
import com.beko.coex.models.ExpenseStatusModel

class ExpenseAdapter() : ListAdapter<Expense,ExpenseAdapter.CustomViewHolder>(customCallBack){

    private var onItemCoinClickListener: ((expense : Expense) -> Unit)? = null

    fun setOnItemCoinClickListener(onItemCoinClickListener: ((expense : Expense) -> Unit)?) {
        this.onItemCoinClickListener = onItemCoinClickListener
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        return CustomViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.expense_item,
                parent,
                false
            )
        )
    }
    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
    inner class CustomViewHolder(private val binding: ExpenseItemBinding) : RecyclerView.ViewHolder(binding.root){
        init {
            binding.root.setOnClickListener {
                onItemCoinClickListener?.invoke(
                    getItem(adapterPosition)
                )
            }
        }
        fun bind(expense: Expense ) {
            with(binding) {
                expenseModel = expense
            }
        }
    }
    companion object {
        val customCallBack = object : DiffUtil.ItemCallback<Expense>() {
            override fun areItemsTheSame(oldItem: Expense, newItem: Expense): Boolean {
                return oldItem.expenseName == newItem.expenseName
            }

            override fun areContentsTheSame(oldItem: Expense, newItem: Expense): Boolean {
                return oldItem == newItem
            }
        }
    }


}