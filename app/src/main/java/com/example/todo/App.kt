package com.example.todo

import android.app.Application
import androidx.room.Room
import com.example.todo.room.AppDataBase

class App: Application() {
    companion object{
        lateinit var appDataBase: AppDataBase
    }

    override fun onCreate() {
        super.onCreate()
        appDataBase = Room.databaseBuilder(
            applicationContext,
            AppDataBase::class.java, "our-database"
        ).allowMainThreadQueries().build()
    }
}