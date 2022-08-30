package com.example.todo.ui.inter

import com.example.todo.ui.fragments.models.CreateDataModel

interface OnItemClickHome {
    fun update(taskModel: CreateDataModel)

    fun delete(taskModel: CreateDataModel)
}