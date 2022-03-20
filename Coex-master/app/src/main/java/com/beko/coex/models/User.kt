package com.beko.coex.models

import android.os.Parcelable
import com.google.firebase.firestore.DocumentId


data class User(
    @DocumentId
    val name:String = "",
    val email:String = "",
)
