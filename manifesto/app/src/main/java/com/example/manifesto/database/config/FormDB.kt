package com.example.manifesto.database.config

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.manifesto.database.dao.FormDAO
import com.example.manifesto.database.models.FormEntity

@Database(entities = [FormEntity::class], version = 5, exportSchema = false)
abstract class FormDB: RoomDatabase(){
    abstract fun formDao(): FormDAO

}
