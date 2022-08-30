package com.example.todo.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.todo.ui.fragments.models.CreateDataModel

@Dao
interface TaskDao {
    @Insert
    fun insert(taskModel: CreateDataModel)

    @Query("SELECT * FROM table_name")
    fun getAll(): LiveData<List<CreateDataModel>>

    @Update
    fun updateData(taskModel: CreateDataModel)

    @Delete
    fun deleteData(taskModel: CreateDataModel)
}