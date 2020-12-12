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

        val top_url = intent.getStringExtra("top")
        val side_rul = intent.getStringExtra("side")
        Timber.i("top_url: $top_url, side_url: $side_rul")

        val topPlayer = findViewById<YouTubePlayerView>(R.id.player_top)
        val sidePlayer = findViewById<YouTubePlayerView>(R.id.player_side)

        topPlayer.play("Yc1fcSJCJ8I")
        sidePlayer.play("Iq037i_c0lc")

    }
}