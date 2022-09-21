package com.example.manifesto2.database.config

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.manifesto2.database.dao.FormDAO
import com.example.manifesto2.database.model.FormEntity

@Database(entities = [FormEntity::class], version = 1)
abstract class FormDB: RoomDatabase(){

    abstract fun formDao(): FormDAO

}