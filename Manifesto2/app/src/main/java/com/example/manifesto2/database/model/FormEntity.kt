package com.example.manifesto2.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "person_information_table")
data class FormEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Long,

    @ColumnInfo(name = "full_name")
    val fullName: String,

    @ColumnInfo(name = "phone_number")
    val phoneNumber: Long,

    @ColumnInfo(name = "email")
    val email: String,

    @ColumnInfo(name = "emergency_number")
    val emergencyNumber: Long,

    @ColumnInfo(name = "emergency_name")
    val emergencyName: String
)