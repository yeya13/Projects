package com.example.hearthstone.data.model.modelGooglePlaces

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
class Geometry (
    @SerializedName("location")
    var location : Location
):Parcelable
