package com.beko.coex.utils

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

object FirebasePath {
    private const val RELEASE_TYPE = "master"

    val userRef = Firebase.firestore.collection("database")
        .document(RELEASE_TYPE)
        .collection("users")
}