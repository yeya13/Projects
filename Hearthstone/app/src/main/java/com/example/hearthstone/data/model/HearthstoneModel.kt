package com.example.hearthstone.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
class HearthstoneModel (
    @SerializedName("classes")
    var classes: List<String>
    ):Parcelable
