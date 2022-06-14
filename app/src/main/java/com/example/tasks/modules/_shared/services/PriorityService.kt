package com.example.tasks.modules._shared.services

import com.example.tasks.modules._shared.models.PriorityModel
import retrofit2.Call
import retrofit2.http.GET

interface PriorityService {
    @GET("priority")
    fun getAll(): Call<List<PriorityModel>>
}