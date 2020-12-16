package com.example.codingnabi.blockcoding

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.codingnabi.R
import kr.co.prnd.YouTubePlayerView
import timber.log.Timber

class CodingVideoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coding_video)

        val url = intent.getStringExtra("url")
        Timber.i("Video Url: $url")

        val player = findViewById<YouTubePlayerView>(R.id.youtube_player)

        url?.let {
            player.play(it)
        }

    }
}