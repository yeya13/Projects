package com.example.manifesto2.database.config

import android.app.Application
import android.content.Context
import androidx.room.Room

class FormApp: Application(){

    companion object {
        lateinit var db: FormDB
    }

    override fun onCreate() {
        super.onCreate()
        db=Room.databaseBuilder(
            this,
            FormDB::class.java,
            "personal"
        ).build()
    }
}