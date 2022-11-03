package com.example.hearthstone.database.config

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.hearthstone.database.dao.HearthstoneDAO
import com.example.hearthstone.database.model.HearthstoneEntity

@Database(entities = [HearthstoneEntity::class], version = 3, exportSchema = false)
abstract class HearthstoneDB: RoomDatabase() {
    abstract fun hearthstoneDao(): HearthstoneDAO
}
