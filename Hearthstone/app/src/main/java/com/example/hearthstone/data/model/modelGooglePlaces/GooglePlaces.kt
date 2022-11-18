package com.example.hearthstone.data.model.modelGooglePlaces

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GooglePlaces(
    @SerializedName("results")
    val results: List<ResultProperties>
):Parcelable
