package com.example.hearthstone.database.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "hearthstone_table")
data class HearthstoneEntity(
    @PrimaryKey(autoGenerate = false)
    var id: String = "",
    var name: String?,
    var type: String?,
    var rarity: String?,
    var cardSet: String?,
    var img: String?
):Parcelable
