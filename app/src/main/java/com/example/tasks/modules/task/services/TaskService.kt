package com.example.tasks.modules.task.services

import com.example.tasks.modules.task.models.TaskModel
import retrofit2.Call
import retrofit2.http.*

interface TaskService {
    @GET("task")
    fun getAll(): Call<List<TaskModel>>

    @GET("task/next7days")
    fun getNextWeek(): Call<List<TaskModel>>

    @GET("task/overdue")
    fun getOverdue(): Call<List<TaskModel>>

    @GET("task/{id}")
    fun getById(
        @Path(value = "id", encoded = true) id: Int
    ): Call<TaskModel>

    @POST("task")
    @FormUrlEncoded
    fun create(
        @Field("PriorityId") priorityId: Int,
        @Field("Description") description: String,
        @Field("DueDate") dueDate: String,
        @Field("Complete") complete: Boolean
    ): Call<Boolean>

    @HTTP(method = "PUT", path = "task", hasBody = true)
    @FormUrlEncoded
    fun update(
        @Field("Id") id: Int,
        @Field("PriorityId") priorityId: Int,
        @Field("Description") description: String,
        @Field("DueDate") dueDate: String,
        @Field("Complete") complete: Boolean
    ): Call<Boolean>

    @HTTP(method = "PUT", path = "task/complete", hasBody = true)
    @FormUrlEncoded
    fun complete(@Field("Id") id: Int): Call<Boolean>

    @HTTP(method = "PUT", path = "task/undo", hasBody = true)
    @FormUrlEncoded
    fun undo(@Field("Id") id: Int): Call<Boolean>

    @HTTP(method = "DELETE", path = "task", hasBody = true)
    @FormUrlEncoded
    fun delete(@Field("Id") id: Int): Call<Boolean>
}