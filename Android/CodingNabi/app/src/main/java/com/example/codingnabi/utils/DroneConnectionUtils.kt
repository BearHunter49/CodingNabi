package com.example.codingnabi.utils


@ExperimentalUnsignedTypes
object DroneConnectionUtils {

    fun getArmPacket(): ByteArray{
        val packet = getDefaultPacket()
        packet[4] = 0x97u
        packet[5] = 0x97u

        return packet.toByteArray()
    }

    fun getDisArmPacket(): ByteArray{
        val packet = getDefaultPacket()
        packet[4] = 0x98u
        packet[5] = 0x98u

        return packet.toByteArray()
    }

    fun getCalibrationPacket(): ByteArray{
        val packet = getDefaultPacket()
        packet[4] = 0xCDu
        packet[5] = 0xCDu

        return packet.toByteArray()
    }

    fun getSetRgbPacket(): ByteArray{
        val packet = getDefaultControlPacket()
        packet[4] = 0x8Du
        packet[5] = 0x61u
        packet[6] = 0xFAu
        packet[7] = 0xCu
        packet[8] = 0x2u
        packet[9] = 0x0u
        packet[10] = 0xD5u

        return packet.toByteArray()
    }

    fun getControlPacket(roll: Int, pitch: Int, yaw: Int, throttle: Int, aux: Int): ByteArray{
        val packet = getDefaultControlPacket()
        var checkSum = 0x0u

        checkSum = checkSum.xor(packet[3].toUInt())
        checkSum = checkSum.xor(packet[4].toUInt())

        packet[5] = roll.toUByte()
        checkSum = checkSum.xor(roll.toUInt())

        packet[6] = pitch.toUByte()
        checkSum = checkSum.xor(pitch.toUInt())

        packet[7] = yaw.toUByte()
        checkSum = checkSum.xor(yaw.toUInt())

        packet[8] = throttle.toUByte()
        checkSum = checkSum.xor(throttle.toUInt())

        packet[9] = aux.toUByte()
        checkSum = checkSum.xor(aux.toUInt())

        packet[10] = checkSum.toUByte()
        return packet.toByteArray()
    }

    private fun getDefaultControlPacket(): UByteArray{
        val packet = UByteArray(11)

        packet[0] = 0x24u
        packet[1] = 0x4Du
        packet[2] = 0x3Cu
        packet[3] = 0x05u
        packet[4] = 0x95u

        return packet
    }

    private fun getDefaultPacket(): UByteArray{
        val packet = UByteArray(6)
        packet[0] = 0x24u
        packet[1] = 0x4Du
        packet[2] = 0x3Cu
        packet[3] = 0u

        return packet
    }
//    private fun getDefaultPacket(): UByteArray{
//        val packet = UByteArray(18)
//        packet[0] = 0x24u
//        packet[1] = 0x4Du
//        packet[2] = 0x3Cu
//        packet[3] = 0u
//
//        for (i in 6..17){
//            packet[i] = 0u
//        }
//        return packet
//    }
}