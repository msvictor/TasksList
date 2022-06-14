package com.example.tasks.modules._shared.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.tasks.core.Constants
import com.example.tasks.modules._shared.models.PriorityModel

@Dao
interface PriorityDAO {
    @Insert
    fun save(list: List<PriorityModel>)

    @Query("DELETE FROM ${Constants.DATABASE.TABLES.PRIORITY}")
    fun clear()

    @Query("SELECT * FROM ${Constants.DATABASE.TABLES.PRIORITY}")
    fun getAll(): List<PriorityModel>

    @Query("SELECT description FROM ${Constants.DATABASE.TABLES.PRIORITY} WHERE id = :id")
    fun getDescriptionById(id: Int): String
}