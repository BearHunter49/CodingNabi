package com.example.codingnabi.socket

import timber.log.Timber
import java.net.DatagramPacket
import java.net.DatagramSocket
import java.net.InetAddress
import java.net.SocketException

class SocketClientUDP(private val ip: InetAddress, private val port: Int) : SocketClient{

    private val socketUDP: DatagramSocket = DatagramSocket()  // Use default port

    override fun sendMessage(data: String) {
        val datagramPacket = DatagramPacket(data.toByteArray(), data.length, ip, port)

        try {
            socketUDP.send(datagramPacket)
        }catch (e: SocketException){
            throw e
        }
    }

    override fun receiveMessage(size: Int): String {
        val receiveMsg = ByteArray(size)
        val datagramPacket = DatagramPacket(receiveMsg, receiveMsg.size)
        var result = ""

        try {
            socketUDP.receive(datagramPacket)
            result = String(receiveMsg, 0, datagramPacket.length)
            Timber.d("data: ${datagramPacket.data}, ip: ${datagramPacket.address}, port: ${datagramPacket.port}")
        }catch (e: SocketException){
            throw e
        }

        return result
    }

    override fun closeSocket() {
        socketUDP.close()
    }

}