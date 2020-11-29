package com.example.codingnabi.home

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.codingnabi.R
import com.example.codingnabi.data.DatabaseCopier
import com.example.codingnabi.databinding.ActivityMainBinding
import kotlinx.coroutines.*
import timber.log.Timber

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var job: Job
    private lateinit var prefs: SharedPreferences
    private val time = mutableListOf<Long>()

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_CodingNabi)
        super.onCreate(savedInstanceState)
        Timber.i("App Start!")

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        val navHost: NavHostFragment = supportFragmentManager.findFragmentById(R.id.myNavHostFragment) as NavHostFragment
        binding.homeBottomNavigation.setupWithNavController(navHost.navController)

        // Database File Copy Check
        prefs = getSharedPreferences("Pref", MODE_PRIVATE)
        if (!prefs.getBoolean("isFirst", false)) {
            Timber.i("isFirst: false")
            startProgressBar()
            initDatabaseFileCopy()
        }
    }

    /**
     * DB 파일 복사
     */
    private fun initDatabaseFileCopy() {
        Timber.i("start DB Copy")
        job = CoroutineScope(Dispatchers.IO).launch {
            DatabaseCopier.downloadLocalDatabase(this@MainActivity)

            withContext(Dispatchers.Main){
                prefs.edit().apply {
                    putBoolean("isFirst", true)
                    apply()
                }
                stopProgressBar()
            }
            Timber.i("DB copy done.")
        }
    }

    private fun startProgressBar() {
        binding.apply {
            contentProgressbar.visibility = View.VISIBLE
            contentLayout.visibility = View.GONE
        }
    }

    private fun stopProgressBar() {
        binding.apply {
            contentProgressbar.visibility = View.GONE
            contentLayout.visibility = View.VISIBLE
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()  // Cancel coroutine
    }

    override fun onBackPressed() {
        super.onBackPressed()
        time.add(System.currentTimeMillis())
        Toast.makeText(this, "뒤로가기를 한번 더 누르시면 종료됩니다.", Toast.LENGTH_LONG).show()

        if (time.size >= 2) {
            if (time[1] - time[0] <= 2000) {
                super.onBackPressed()
            }
            time.clear()
        }
    }
}