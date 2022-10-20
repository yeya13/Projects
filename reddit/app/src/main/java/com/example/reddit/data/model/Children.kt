package com.example.reddit.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
class Children (
    val data: ArticleModel
): Parcelable