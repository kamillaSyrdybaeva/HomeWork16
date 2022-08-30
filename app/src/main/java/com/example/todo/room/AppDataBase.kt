package com.example.todo.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.todo.ui.fragments.models.CreateDataModel

@Database(entities = [CreateDataModel::class], version = 1)
abstract class AppDataBase: RoomDatabase() {
    abstract fun taskDao(): TaskDao
}