package com.example.reddit.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
class Reddit (
    @SerializedName("data") val data: Data
): Parcelable