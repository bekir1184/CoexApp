package com.beko.coex.models

import android.os.Parcelable
import com.google.firebase.firestore.DocumentId
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    @DocumentId
    val name : String = "",
    val email : String = "",
    var uid : String ="",
    var room : String="",
    var totalCost : Int = 0,
    var billCost : Int = 0,
    var houseCost :Int = 0,
    var foodCost : Int =0,
    var otherCost : Int = 0,
    var approvalStatus : Int = 0 // 0 = waiting for approval , 1 = approved , 2 = approval denied
) : Parcelable
