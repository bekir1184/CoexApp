package com.beko.coex.utils

import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

object Functions {
    fun getCurrentUserUid() = Firebase.auth.currentUser?.uid
}