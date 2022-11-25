package com.example.hearthstone.data.model.modelGooglePlaces

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Location(
    @SerializedName("lat")
    var lat : Double,
    @SerializedName("lng")
    var lng : Double
):Parcelable
