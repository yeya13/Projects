package com.example.reddit.data.network.api

import com.example.reddit.data.model.ArticleModel
import com.example.reddit.data.model.Reddit
import retrofit2.Response
import retrofit2.http.GET

interface RedditApi {

    @GET("androiddev.json")
    suspend fun getAuthor(): Response<Reddit>
}