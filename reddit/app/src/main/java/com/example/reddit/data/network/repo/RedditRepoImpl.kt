package com.example.reddit.data.network.repo

import android.util.Log
import com.example.reddit.data.model.ArticleModel
import com.example.reddit.data.model.Result
import com.example.reddit.data.model.Reddit
import com.example.reddit.data.network.api.RedditApi
import com.example.reddit.data.network.retrofit.SingletonRetrofit
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RedditRepoImpl: RedditRepo {
    private val singletonRetrofit = SingletonRetrofit.retrofitProvider(
        serviceClass = RedditApi::class.java,
        baseUrl = "https://www.reddit.com/r/")

    override suspend fun getAuthor(): Result<Reddit?> {
        return withContext(Dispatchers.IO){
            val apiRedditApi = singletonRetrofit
            val response = apiRedditApi.getAuthor()
            if(response.isSuccessful) {
                Result.Success(response.body())
            }else{
                Result.Error(Exception(response.errorBody().toString()))
            }
        }
    }
}