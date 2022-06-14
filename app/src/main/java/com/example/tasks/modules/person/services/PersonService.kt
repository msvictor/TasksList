package com.example.tasks.modules.person.services

import com.example.tasks.modules._shared.models.HeaderModel
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.POST
import retrofit2.http.FormUrlEncoded

interface PersonService {
    @POST("authentication/login")
    @FormUrlEncoded
    fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<HeaderModel>

    @POST("authentication/create")
    @FormUrlEncoded
    fun create(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("receivenews") news: Boolean
    ): Call<HeaderModel>
}