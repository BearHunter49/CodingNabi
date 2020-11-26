package com.example.codingnabi.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.codingnabi.R
import timber.log.Timber

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_CodingNabi)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Timber.i("App Start!")
    }
}