package com.example.manifesto.database.dao

import androidx.room.*
import com.example.manifesto.database.models.FormEntity

@Dao
interface FormDAO {

    @Query("SELECT * FROM person_information_table ORDER BY id DESC")
    fun getAll(): List<FormEntity>

    @Query("SELECT * FROM person_information_table WHERE id = :id")
    fun getById(id: Long):FormEntity

    @Insert
    fun insertPerson(personas: List<FormEntity>):List<Long>

    @Delete
    fun deletePerson(personal: FormEntity):Int

    @Update
    fun updatePerson(personal: FormEntity):Int
}