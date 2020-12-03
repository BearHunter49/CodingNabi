package com.example.codingnabi.socket

interface SocketClient {
    fun sendMessage(data: String)
    fun receiveMessage(size: Int): String
    fun closeSocket()
}