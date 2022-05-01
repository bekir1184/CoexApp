package com.beko.coex.models

data class Room(
    val name : String = "" ,
    val password : String ="",
    val userUidList : MutableList<String> = mutableListOf(),
    val expenseList : List<Expense> = emptyList(),
    val totalExpense : Int = 0,
    val totalBill : Int = 0 ,
    val totalFood : Int = 0 ,
    val totalOther : Int = 0,
    val totalRent : Int = 0,
)
