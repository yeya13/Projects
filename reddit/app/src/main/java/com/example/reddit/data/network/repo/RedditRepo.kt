package com.example.reddit.data.network.repo

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.reddit.data.model.ArticleModel
import com.example.reddit.data.model.Reddit
import com.example.reddit.data.model.Result
import kotlinx.coroutines.launch

interface RedditRepo {

    suspend fun getAuthor(): Result<Reddit?>

    companion object {
        fun provideRedditRepoApi() = RedditRepoImpl()
    }
}