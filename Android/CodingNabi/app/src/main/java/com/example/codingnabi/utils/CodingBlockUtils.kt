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
    // Local: 192.168.0.24
    private val socketClient: SocketClient by lazy {
        SocketClientUdp(
            InetAddress.getByName("192.168.4.1"),
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

        val positionList: MutableList<Float> = mutableListOf()
        linearLayout.children.forEach { child ->
            Timber.d("${child.x + child.width / 2}, ${child.y + child.height / 2}")

            // *important* Must get view's Center!
            positionList.add(child.y + child.height / 2)
        }

        for (i in 0 until childCount){
            if (y < positionList[i]) return i
        }

        return childCount
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
    suspend fun setDroneArm() {
        socketClient.sendMessage(DroneConnectionUtils.getArmPacket())
    }

    /***
     * Drone 수평 계산
     */
    @ExperimentalUnsignedTypes
    suspend fun setDroneCalibration() {
        socketClient.sendMessage(DroneConnectionUtils.getCalibrationPacket())
    }

    /***
     * Drone 시동 끄기
     */
    @ExperimentalUnsignedTypes
    suspend fun setDroneDisarm() {
        socketClient.sendMessage(DroneConnectionUtils.getDisArmPacket())
    }

    /***
     * Drone RGB 설정
     */
    @ExperimentalUnsignedTypes
    suspend fun setDroneRgb() {
        socketClient.sendMessage(DroneConnectionUtils.getSetRgbPacket())
    }

    /***
     * Drone에게 데이터패킷 전송
     */
    @ExperimentalUnsignedTypes
    suspend fun sendDataByTag(tag: String) {
        val time = System.currentTimeMillis()
        while ((System.currentTimeMillis() - time) / 1000 < 1) {
            delay(10L)
            socketClient.sendMessage(
                when (tag) {
                    "u" -> {
                        DroneConnectionUtils.getControlPacket(125, 125, 125, 165, 0)
                    }
                    "d" -> {
                        DroneConnectionUtils.getControlPacket(125, 125, 125, 105, 0)
                    }
                    "l" -> {
                        DroneConnectionUtils.getControlPacket(105, 125, 125, 125, 0)
                    }
                    "r" -> {
                        DroneConnectionUtils.getControlPacket(145, 125, 125, 125, 0)
                    }
                    "f" -> {
                        DroneConnectionUtils.getControlPacket(125, 165, 125, 125, 0)
                    }
                    "b" -> {
                        DroneConnectionUtils.getControlPacket(125, 115, 125, 125, 0)
                    }
//                "lp" -> {
//                    DroneCommunicationUtils.getControlPacket(125, 125, 125, 70, 0)
//                }
//                "fc" -> {
//                    DroneCommunicationUtils.getControlPacket(125, 125, 125, 70, 0)
                    "fu" -> {
                        DroneConnectionUtils.getControlPacket(125, 125, 125, 195, 0)
                    }

                    else -> {  // Default
                        DroneConnectionUtils.getControlPacket(125, 125, 125, 125, 0)
                    }
                }
            )
        }

    }

    suspend fun getPacket(): String {
        return socketClient.receiveMessage(1000)
    }


}