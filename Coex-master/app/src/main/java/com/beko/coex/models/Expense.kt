package com.beko.coex.models

data class Expense(
    val expenseName : String = "",
    val category : String = "",
    val cost : Double = 0.0,
    val date : String = "",
    val descripton: String= "",
    val user : User = User()
)
