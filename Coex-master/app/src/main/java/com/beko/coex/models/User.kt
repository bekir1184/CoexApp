package com.beko.coex.models

import android.os.Parcelable
import com.google.firebase.firestore.DocumentId
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
    @DocumentId
    val name:String = "",
    val email:String = "",
) : Parcelable
