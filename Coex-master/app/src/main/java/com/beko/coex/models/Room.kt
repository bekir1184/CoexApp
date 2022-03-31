package com.beko.coex.models

data class Room(
    val name : String ,
    val password : String ,
    val Users : List<User>
)
