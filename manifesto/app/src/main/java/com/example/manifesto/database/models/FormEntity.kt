package com.example.manifesto.database.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "person_information_table")
data class FormEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Long,
    val fullName: String,
    val phoneNumber: String,
    val email: String,
    val emergencyNumber: String,
    val emergencyName: String
): Parcelable
