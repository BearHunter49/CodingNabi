package com.example.codingnabi.socket

interface SocketClient {
    fun sendMessage(data: ByteArray)
    fun receiveMessage(size: Int): String
    fun closeSocket()
}