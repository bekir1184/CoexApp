package com.beko.coex.utils

import android.view.View

object Constants {
    fun View.makeVisible() {
        this.visibility = View.VISIBLE
    }

    fun View.makeInvisible() {
        this.visibility = View.GONE
    }
    fun String.isValidMail(): Boolean =
       android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()
}