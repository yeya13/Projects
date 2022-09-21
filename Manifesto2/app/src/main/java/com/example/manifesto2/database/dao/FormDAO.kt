package com.example.manifesto2.database.dao

import androidx.room.*
import com.example.manifesto2.database.model.FormEntity

@Dao
interface FormDAO {

    @Query("SELECT * FROM person_information_table ORDER BY id DESC")
    fun getAll(): List<FormEntity>

    @Insert
    fun insertPerson(personas: List<FormEntity>):List<Long>

    @Delete
    fun deletePerson(personal: FormEntity):Int

    @Update
    fun updatePerson(personal: FormEntity):Int
}