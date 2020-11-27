package com.example.codingnabi.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.codingnabi.R
import com.example.codingnabi.databinding.ActivityMainBinding
import timber.log.Timber

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_CodingNabi)
        super.onCreate(savedInstanceState)
        Timber.i("App Start!")

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        val navHost: NavHostFragment = supportFragmentManager.findFragmentById(R.id.myNavHostFragment) as NavHostFragment

        binding.homeBottomNavigation.setupWithNavController(navHost.navController)

    }
}