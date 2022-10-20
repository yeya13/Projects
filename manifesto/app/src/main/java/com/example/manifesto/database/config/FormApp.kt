package com.example.manifesto.database.config

import android.app.Application
import androidx.room.Room

class FormApp: Application(){

    companion object {
        lateinit var DB3: FormDB
    }

    override fun onCreate() {
        super.onCreate()
        DB3= Room.databaseBuilder(
            this,
            FormDB::class.java,
            "personal2"
        ).build()
    }
}