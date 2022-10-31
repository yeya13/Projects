package com.example.hearthstone.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class HSCardsByClassModel(
    @SerializedName("cardId")
    var cardId: String,
    @SerializedName("name")
    var name: String?,
    @SerializedName("type")
    var type: String?,
    @SerializedName("rarity")
    var rarity: String?,
    @SerializedName("cardSet")
    var cardSet: String?,
    @SerializedName("img")
    var img: String?,
    @SerializedName("text")
    var text: String?,
    @SerializedName("playerClass")
    var playerClass: String?
    ) : Parcelable
