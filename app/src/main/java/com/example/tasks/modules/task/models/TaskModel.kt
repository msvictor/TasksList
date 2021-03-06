package com.example.tasks.modules.task.models

import com.google.gson.annotations.SerializedName

class TaskModel {
    @SerializedName("Id")
    var id: Int = 0

    @SerializedName("PriorityId")
    var priorityId: Int = 0

    @SerializedName("Description")
    lateinit var description: String

    @SerializedName("DueDate")
    lateinit var dueDate: String

    @SerializedName("Complete")
    var complete: Boolean = false
}