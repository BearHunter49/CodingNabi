package com.example.codingnabi.utils


@ExperimentalUnsignedTypes
object DroneCommunicationUtils {

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

    fun getControlPacket(roll: Int, pitch: Int, yaw: Int, throttle: Int, aux: Int): ByteArray{
        val packet = getDefaultControlPacket()
        var checkSum = packet[10]

        packet[5] = roll.toUByte()
        checkSum = checkSum.xor(roll.toUByte())

        packet[6] = pitch.toUByte()
        checkSum = checkSum.xor(pitch.toUByte())

        packet[7] = yaw.toUByte()
        checkSum = checkSum.xor(yaw.toUByte())

        packet[8] = throttle.toUByte()
        checkSum = checkSum.xor(throttle.toUByte())

        packet[9] = aux.toUByte()
        checkSum = checkSum.xor(aux.toUByte())

        packet[10] = checkSum

        return packet.toByteArray()
    }

    private fun getDefaultControlPacket(): UByteArray{
        val packet = UByteArray(11)
        packet[0] = 0x24u
        packet[1] = 0x4Du
        packet[2] = 0x3Cu
        packet[3] = 0x05u
        packet[4] = 0x95u
        packet[10] = 0x00u
        return packet
    }

    private fun getDefaultPacket(): UByteArray{
        val packet = UByteArray(18)
        packet[0] = 0x24u
        packet[1] = 0x4Du
        packet[2] = 0x3Cu
        packet[3] = 0u

        for (i in 6..17){
            packet[i] = 0u
        }
        return packet
    }
}