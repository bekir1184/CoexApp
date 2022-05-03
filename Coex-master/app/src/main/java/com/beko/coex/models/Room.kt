package com.beko.coex.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class Room(
    val name : String = "",
    val password : String ="",
    val userUidList : MutableList<String> = mutableListOf(),
    val expenseList :@RawValue MutableList<Expense> = mutableListOf(),
    var totalExpense : Int = 0,
    var totalBill : Int = 0,
    var totalFood : Int = 0,
    var totalOther : Int = 0,
    var totalRent : Int = 0,
    var approvalStarted : Int = 0, // 0 = approvel not started , 1 = approvel started
) : Parcelable
