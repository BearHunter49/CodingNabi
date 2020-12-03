package com.example.codingnabi.utils

import android.content.Context
import android.content.res.ColorStateList
import android.view.View
import android.widget.LinearLayout
import androidx.core.view.children
import com.example.codingnabi.R
import com.example.codingnabi.socket.SocketClient
import com.example.codingnabi.socket.SocketClientUdp
import kotlinx.coroutines.delay
import timber.log.Timber
import java.net.InetAddress

object CodingBlockUtils {
    // Drone: 192.168.4.1
    private val socketClient: SocketClient by lazy {
        SocketClientUdp(
            InetAddress.getByName("192.168.0.24"),
            5000
        )
    }

    /***
     * Make block view by tag
     * @param context: need to use resources
     * @param block: block tag
     */
    fun getBlock(context: Context, block: String): View = BlockFactory.makeBlockView(context, block)

    /***
     * Calculate position that block insert to from LinearLayout.
     */
    fun calculatePosition(linearLayout: LinearLayout, y: Float): Int {
        val childCount = linearLayout.childCount

        if (childCount == 0) return 0  // First block

        val positionList: MutableList<Pair<Int, Float>> = mutableListOf()
        linearLayout.children.forEachIndexed { i, child ->
            Timber.d("${child.x + child.width / 2}, ${child.y + child.height / 2}")

            // *important* Must get view's Center!
            positionList.add(Pair(i, child.y + child.height / 2))
        }

        if (y < positionList.first().second) return 0  // 맨 위
        if (y > positionList.last().second) return childCount  // 맨 아래

        // 사이 비교
        for (i in 0 until childCount - 1) {
            if (positionList[i].second < y && y < positionList[i + 1].second) return i + 1
        }

        return 0
    }

    /***
     * Get original color of block
     */
    fun getOriginalColor(context: Context, tag: String): ColorStateList {
        val resources = context.resources

        return when (tag) {
            "u", "d" -> {
                resources.getColorStateList(R.color.block_up_down, null)
            }
            "l", "r" -> {
                resources.getColorStateList(R.color.block_left_right, null)
            }
            "f", "b" -> {
                resources.getColorStateList(R.color.block_forward_backward, null)
            }
            "lp" -> {
                resources.getColorStateList(R.color.block_loop, null)
            }
            "fc" -> {
                resources.getColorStateList(R.color.block_function, null)
            }
            else -> {
                resources.getColorStateList(R.color.design_default_color_error, null)
            }
        }
    }

    /***
     * Drone 시동 켜기
     */
    @ExperimentalUnsignedTypes
    suspend fun setDroneUsable() {
        socketClient.sendMessage(DroneCommunicationUtils.getArmPacket())
        delay(500L)
        socketClient.sendMessage(DroneCommunicationUtils.getCalibrationPacket())
    }

    /***
     * Drone 시동 끄기
     */
    @ExperimentalUnsignedTypes
    suspend fun setDroneDisable() {
        socketClient.sendMessage(DroneCommunicationUtils.getDisArmPacket())
    }

    /***
     * Drone에게 데이터패킷 전송
     */
    @ExperimentalUnsignedTypes
    suspend fun sendDataByTag(tag: String) {
        val curTime = System.currentTimeMillis()
        while ((System.currentTimeMillis() - curTime) / 1000 < 1){
            socketClient.sendMessage(
                when (tag) {
                    "u" -> {
                        DroneCommunicationUtils.getControlPacket(125, 125, 125, 70, 0)
                    }
                    "d" -> {
                        DroneCommunicationUtils.getControlPacket(125, 125, 125, 70, 0)
                    }
                    "l"-> {
                        DroneCommunicationUtils.getControlPacket(125, 125, 125, 70, 0)
                    }
                    "r" -> {
                        DroneCommunicationUtils.getControlPacket(125, 125, 125, 70, 0)
                    }
                    "f" -> {
                        DroneCommunicationUtils.getControlPacket(125, 125, 125, 70, 0)
                    }
                    "b" -> {
                        DroneCommunicationUtils.getControlPacket(125, 125, 125, 70, 0)
                    }
//                "lp" -> {
//                    DroneCommunicationUtils.getControlPacket(125, 125, 125, 70, 0)
//                }
//                "fc" -> {
//                    DroneCommunicationUtils.getControlPacket(125, 125, 125, 70, 0)
                    else -> {
                        DroneCommunicationUtils.getControlPacket(125, 125, 125, 70, 0)
                    }
                }
            )
        }


    }
}