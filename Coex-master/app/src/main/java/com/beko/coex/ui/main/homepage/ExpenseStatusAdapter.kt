package com.beko.coex.ui.main.homepage

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.beko.coex.R
import com.beko.coex.databinding.OneRowExpenseStatusBinding
import com.beko.coex.models.ExpenseStatusModel

class ExpenseStatusAdapter()  : ListAdapter<ExpenseStatusModel,ExpenseStatusAdapter.CustomViewHolder>(customCallBack) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        return CustomViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.one_row_expense_status,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
    inner class CustomViewHolder(private val binding: OneRowExpenseStatusBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(statusModel : ExpenseStatusModel) {
            with(binding) {
                expenseStatusModel = statusModel
            }
        }
    }
    companion object {
        val customCallBack = object : DiffUtil.ItemCallback<ExpenseStatusModel>() {
            override fun areItemsTheSame(oldItem: ExpenseStatusModel, newItem: ExpenseStatusModel): Boolean {
                return oldItem.cost == newItem.cost
            }

            override fun areContentsTheSame(oldItem: ExpenseStatusModel, newItem: ExpenseStatusModel): Boolean {
                return oldItem == newItem
            }
        }
    }


}