package com.beko.coex.models

data class Expense(
    val category : String ,
    val cost : Double,
    val date : String,
    val descripton: String,
    val user : User
)
