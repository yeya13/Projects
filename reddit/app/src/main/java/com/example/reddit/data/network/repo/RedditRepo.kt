package com.example.reddit.data.network.repo

import com.example.reddit.data.model.Reddit
import com.example.reddit.data.model.Result

interface RedditRepo {

    suspend fun getAuthor(): Result<Reddit?>

}
