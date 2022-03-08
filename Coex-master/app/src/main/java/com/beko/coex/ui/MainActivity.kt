package com.beko.coex.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.beko.coex.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}