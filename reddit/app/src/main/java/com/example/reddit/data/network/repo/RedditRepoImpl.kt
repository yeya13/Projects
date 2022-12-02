package com.example.reddit.data.network.repo

import com.example.reddit.data.model.Reddit
import com.example.reddit.data.model.Result
import com.example.reddit.data.network.api.RedditApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RedditRepoImpl @Inject constructor(
    private val api: RedditApi,
    private val dispatcher: Dispatchers
): RedditRepo {


    override suspend fun getAuthor(): Result<Reddit?> {
        return withContext(dispatcher.IO){
            val apiRedditApi = api
            val response = apiRedditApi.getAuthor()
            if(response.isSuccessful) {
                Result.Success(response.body())
            }else{
                Result.Error(Exception(response.errorBody().toString()))
            }
        }
    }
}
