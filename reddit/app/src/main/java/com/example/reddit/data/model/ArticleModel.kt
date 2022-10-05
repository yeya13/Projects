package com.example.reddit.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ArticleModel(
    @SerializedName("author") var author:String?,
    @SerializedName("title") val title: String?,
    @SerializedName("num_comments") var num_comments: String?,
    @SerializedName("selftext") val selftext: String?,
    @SerializedName("url") val url: String
    ):Parcelable
