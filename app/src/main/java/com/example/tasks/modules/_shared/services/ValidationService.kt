package com.example.tasks.modules._shared.services

class ValidationService(message: String = "") {
    private var mStatus: Boolean = true

    private lateinit var mMessage: String

    init {
        if (message.isNotEmpty()) {
            mStatus = false
            mMessage = message
        }
    }

    fun isSuccess() = mStatus

    fun getMessage() = mMessage
}