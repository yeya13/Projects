package com.example.manifesto.database.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "person_information_table")
data class FormEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val fullName: String,
    val phoneNumber: String,
    val email: String,
    val emergencyNumber: String,
    val emergencyName: String
)