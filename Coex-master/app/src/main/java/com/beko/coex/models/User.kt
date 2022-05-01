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
    val totalCost : Int = 0,
    val billCost : Int = 0,
    val houseCost :Int = 0,
    val foodCost : Int =0,
    val otherCost : Int = 0,
) : Parcelable
