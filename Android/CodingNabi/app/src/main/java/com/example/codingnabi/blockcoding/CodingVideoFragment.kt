package com.example.codingnabi.blockcoding

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.codingnabi.R
import kr.co.prnd.YouTubePlayerView
import timber.log.Timber

/***
 * 외부 라이브러리 YouTubePlayerView binding으로 View 인식 불가
 */
class CodingVideoFragment : Fragment() {
    private lateinit var top_url: String
    private lateinit var side_url: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        top_url = arguments?.getString("top") ?: ""
        side_url = arguments?.getString("side") ?: ""
        Timber.i("top: $top_url, side: $side_url")

        return inflater.inflate(R.layout.fragment_coding_video, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val topPlayer = view?.findViewById<YouTubePlayerView>(R.id.youtube_player_top)
//        val sidePlayer = view?.findViewById<YouTubePlayerView>(R.id.youtube_player_side)

        topPlayer?.play("Yc1fcSJCJ8I")
//        sidePlayer?.play("Iq037i_c0lc")
    }

    override fun onDestroyView() {
        super.onDestroyView()

        val topPlayer = view?.findViewById<YouTubePlayerView>(R.id.youtube_player_top)
        topPlayer
    }


}