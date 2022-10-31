package com.example.hearthstone.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.hearthstone.database.model.HearthstoneEntity

@Dao
interface HearthstoneDAO {

    @Query("SELECT * FROM hearthstone_table ORDER BY id DESC")
    fun getAll(): List<HearthstoneEntity>

    @Insert
    fun insertCard(card: HearthstoneEntity)

    @Delete
    fun removeCard(card: HearthstoneEntity): Int

    @Query("SELECT * FROM hearthstone_table WHERE id = :cardId")
    fun doDataQuery(cardId: String): HearthstoneEntity?

    @Query("SELECT id FROM hearthstone_table")
    fun getAllId(): List<String>
}
