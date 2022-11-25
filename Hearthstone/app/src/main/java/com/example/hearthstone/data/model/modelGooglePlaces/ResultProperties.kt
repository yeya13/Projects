package com.example.hearthstone.data.model.modelGooglePlaces

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ResultProperties(
    @SerializedName("name")
    var name: String,
    @SerializedName("icon")
    var icon: String,
    @SerializedName("geometry")
    var geometry: Geometry,
    @SerializedName("types")
    var types: List<String>
) : Parcelable
