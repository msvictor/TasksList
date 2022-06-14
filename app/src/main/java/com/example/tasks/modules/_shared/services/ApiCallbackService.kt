package com.example.tasks.modules._shared.services

interface ApiCallbackService<T> {
    fun onSuccess(data: T)

    fun onFailure(message: String)
}