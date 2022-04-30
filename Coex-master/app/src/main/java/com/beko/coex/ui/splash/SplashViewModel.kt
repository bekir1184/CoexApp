package com.beko.coex.ui.splash

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    val firebaseAuth: FirebaseAuth
) : ViewModel() {
    fun userLogin() : Boolean {
        val currentUser = firebaseAuth.currentUser
        return currentUser != null
    }
}