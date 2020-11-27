package com.example.codingnabi.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.codingnabi.R
import com.example.codingnabi.data.DatabaseCopier
import com.example.codingnabi.data.RepositoryFactory
import com.example.codingnabi.databinding.ActivityMainBinding
import kotlinx.coroutines.*
import timber.log.Timber

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var job: Job

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_CodingNabi)
        super.onCreate(savedInstanceState)
        Timber.i("App Start!")

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        val navHost: NavHostFragment = supportFragmentManager.findFragmentById(R.id.myNavHostFragment) as NavHostFragment
        binding.homeBottomNavigation.setupWithNavController(navHost.navController)

        // Database File Copy Check
        initDatabaseFileCopy()
    }

    /**
     * DB 파일을 복사함과 동시에 Repository 인스턴스를 모두 생성한다.
     */
    private fun initDatabaseFileCopy() {
        job = CoroutineScope(Dispatchers.IO).launch {
            DatabaseCopier.downloadLocalDatabase(this@MainActivity)
            RepositoryFactory.create(DatabaseCopier.getInstance(this@MainActivity))

            withContext(Dispatchers.Main){
                stopProgressBar()
            }
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
}