package com.example.tasks.infra

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.tasks.core.Constants
import com.example.tasks.modules._shared.dao.PriorityDAO
import com.example.tasks.modules._shared.models.PriorityModel

@Database(entities = [PriorityModel::class], version = Constants.DATABASE.VERSION)
abstract class DatabaseHelper : RoomDatabase() {

    companion object {
        private lateinit var INSTANCE: DatabaseHelper

        fun getDatabase(context: Context): DatabaseHelper {
            if (!Companion::INSTANCE.isInitialized) {
                synchronized(DatabaseHelper::class) {
                    INSTANCE = Room.databaseBuilder(
                        context,
                        DatabaseHelper::class.java,
                        Constants.DATABASE.NAME
                    )
                        .allowMainThreadQueries()
                        .build()
                }
            }
            return INSTANCE
        }
    }

    abstract fun priorityDAO(): PriorityDAO
}