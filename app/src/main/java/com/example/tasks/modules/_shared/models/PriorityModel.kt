package com.example.tasks.modules._shared.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.tasks.core.Constants
import com.google.gson.annotations.SerializedName

@Entity(tableName = Constants.DATABASE.TABLES.PRIORITY)
class PriorityModel {
    @SerializedName("Id")
    @ColumnInfo(name = "id")
    @PrimaryKey
    var id: Int = 0

    @SerializedName("Description")
    @ColumnInfo(name = "description")
    lateinit var description: String
}